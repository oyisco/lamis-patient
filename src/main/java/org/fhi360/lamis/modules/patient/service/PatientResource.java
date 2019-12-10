package org.fhi360.lamis.modules.patient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import com.github.vanroy.springdata.jest.aggregation.AggregatedPage;
import io.github.jhipster.web.util.ResponseUtil;
import io.searchbox.core.search.aggregation.TermsAggregation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.fhi360.lamis.modules.base.domain.entities.User;
import org.fhi360.lamis.modules.base.service.ElasticsearchIndexService;
import org.fhi360.lamis.modules.base.web.errors.BadRequestAlertException;
import org.fhi360.lamis.modules.base.web.util.AggregateUtil;
import org.fhi360.lamis.modules.base.web.util.HeaderUtil;
import org.fhi360.lamis.modules.base.web.util.PaginationUtil;
import org.fhi360.lamis.modules.base.web.vm.AggregateVM;
import org.fhi360.lamis.modules.patient.domain.entities.Patient;
import org.fhi360.lamis.modules.patient.domain.repositories.LamisUserRepository;
import org.fhi360.lamis.modules.patient.domain.repositories.PatientRepository;
import org.fhi360.lamis.modules.patient.domain.repositories.search.PatientSearchRepository;
import org.fhi360.lamis.modules.patient.service.util.FilterUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class PatientResource {
    private static final String ENTITY_NAME = "patient";

    private final PatientRepository patientRepository;
    private final PatientSearchRepository patientSearchRepository;
    private final JestElasticsearchTemplate searchTemplate;
    private final ElasticsearchIndexService indexService;
    private final LamisUserRepository userRepository;

    public PatientResource(PatientRepository patientRepository, PatientSearchRepository patientSearchRepository,
                           ElasticsearchOperations searchTemplate, ElasticsearchIndexService indexService, LamisUserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.patientSearchRepository = patientSearchRepository;
        this.searchTemplate = (JestElasticsearchTemplate) searchTemplate;
        this.indexService = indexService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /patients : Create a new patient.
     *
     * @param patient the patient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patient, or with status 400 (Bad Request) if the patient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patients")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) throws URISyntaxException {
        LOG.debug("REST request to save Patient : {}", patient);
        if (patient.getId() != null) {
            throw new BadRequestAlertException("A new patient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Patient result = patientRepository.save(patient);
        patientSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/patients/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /patients : Updates an existing patient.
     *
     * @param patient the patient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patient,
     * or with status 400 (Bad Request) if the patient is not valid,
     * or with status 500 (Internal Server Error) if the patient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patients")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) throws URISyntaxException {
        LOG.debug("REST request to update Patient : {}", patient);
        if (patient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Patient result = patientRepository.save(patient);
        LOG.info("Result: {}", result);
        patientSearchRepository.save(result);
        LOG.info("Modified search object");
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patient.getId().toString()))
                .body(result);
    }

    /**
     * GET  /patients : get all the patients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patients in body
     */
    @GetMapping("/patients")
    public List<Patient> getAllPatients(Pageable pageable) {
        LOG.debug("REST request to get all Patients");

        return patientRepository.findAll(pageable).getContent();
    }

    /**
     * POST  /patients : get all the patients via Elasticsearch.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patients in body
     */
    @PostMapping("/_search/patients")
    public ResponseEntity<List<Patient>> getAllPatients1(@RequestParam String query, @RequestBody List<AggregateVM> aggregates,
                                                         Pageable pageable) {
        LOG.debug("REST request to search Patients: {}; page: {}", query, pageable);
        TermsAggregationBuilder genderAggregation = AggregationBuilders.terms("gender_tags")
                .field("gender")
                .order(BucketOrder.count(true));
        TermsAggregationBuilder statusAggregation = AggregationBuilders.terms("status_tags")
                .field("currentStatus")
                .order(BucketOrder.count(true));
        TermsAggregationBuilder entryAggregation = AggregationBuilders.terms("entry_tags")
                .field("entryPoint")
                .order(BucketOrder.count(true));
        TermsAggregationBuilder biometricAggregation = AggregationBuilders.terms("biometric_tags")
                .field("biometric")
                .order(BucketOrder.count(true));
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
                .withIndices("patient")
                .addAggregation(genderAggregation)
                .addAggregation(statusAggregation)
                .addAggregation(biometricAggregation)
                .addAggregation(entryAggregation)
                .withPageable(pageable);

        BoolQueryBuilder filter = FilterUtil.getFacilityFilterForCurrentUser();
        for (AggregateVM aggregate : aggregates) {
            if (StringUtils.isNotBlank(aggregate.getField())) {
                filter = filter
                        .filter(QueryBuilders.termQuery(aggregate.getField(), aggregate.getKey()));
            }
        }

        if (StringUtils.isBlank(query)) {
            queryBuilder = queryBuilder.withQuery(
                    QueryBuilders.boolQuery()
                            .filter(filter)
                            .must(QueryBuilders.matchAllQuery())
            );
        } else {
            QueryBuilder booleanQuery = QueryBuilders.boolQuery();
            BoolQueryBuilder builder = QueryBuilders.boolQuery()
                    .should(
                            QueryBuilders.matchQuery("surname", query)
                                    .fuzziness(Fuzziness.ONE)
                                    .prefixLength(1)
                    )
                    .should(
                            QueryBuilders.matchQuery("otherNames", query)
                                    .fuzziness(Fuzziness.ONE)
                                    .prefixLength(1)
                    )
                    .should(
                            QueryBuilders.termQuery("hospitalNum", query)
                                    .boost(50)
                    )
                    .should(
                            QueryBuilders.termQuery("uniqueId", query)
                                    .boost(50)
                    )
                    .should(
                            QueryBuilders.termQuery("phone", query)
                                    .boost(50)
                    );

            if (aggregates.size() > 0) {
                builder = builder.filter(filter);
            }
            queryBuilder = queryBuilder.withQuery(builder);
        }
        SearchQuery searchQuery = queryBuilder.build()
                .setPageable(pageable)
                .addSort(pageable.getSort());

        AggregatedPage<Patient> page = searchTemplate.queryForPage(searchQuery, Patient.class);
        List<AggregateVM> responseAggregates = new ArrayList<>();

        if (page.hasAggregations()) {
            TermsAggregation aggregation = page.getAggregation("status_tags", TermsAggregation.class);
            responseAggregates.addAll(
                    AggregateUtil.getAggregates(aggregation, "currentStatus", "Current Status"));
            aggregation = page.getAggregation("entry_tags", TermsAggregation.class);
            responseAggregates.addAll(
                    AggregateUtil.getAggregates(aggregation, "entryPoint", "Entry Point"));
            aggregation = page.getAggregation("gender_tags", TermsAggregation.class);
            responseAggregates.addAll(
                    AggregateUtil.getAggregates(aggregation, "gender", "Gender"));
            aggregation = page.getAggregation("biometric_tags", TermsAggregation.class);
            responseAggregates.addAll(
                    AggregateUtil.getAggregates(aggregation, "biometric", "Has Biometrics"));
        }

        Map<String, List<AggregateVM>> map = AggregateUtil.aggregateMap(responseAggregates);

        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/patients");
        try {
            String aggs = objectMapper.writeValueAsString(map);
            headers.add("Aggregates", aggs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /patients/:id : get the "id" patient.
     *
     * @param id the id of the patient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patient, or with status 404 (Not Found)
     */
    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        LOG.debug("REST request to get Patient : {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(patient);
    }

    /**
     * DELETE  /patients/:id : delete the "id" patient.
     *
     * @param id the id of the patient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        LOG.debug("REST request to delete Patient : {}", id);
        patientRepository.deleteById(id);
        patientSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/patients/test")
    public List<? extends User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/patients/re-index")
    public void reindex() {
        indexService.reindexAll(Patient.class, patientRepository, patientSearchRepository);
    }
}

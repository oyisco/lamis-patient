package org.fhi360.lamis.modules.patient.domain.repositories.search;

import org.fhi360.lamis.modules.patient.domain.entities.Patient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PatientSearchRepository extends ElasticsearchRepository<Patient, Long> {
}

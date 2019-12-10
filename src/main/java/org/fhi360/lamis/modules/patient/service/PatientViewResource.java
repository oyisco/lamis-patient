package org.fhi360.lamis.modules.patient.service;

import lombok.extern.slf4j.Slf4j;
import org.fhi360.lamis.modules.base.web.rest.TemplateResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class PatientViewResource {
    private final static String MODULE_NAME = "LAMISPatientModule";

    private final TemplateResource templateResource;

    public PatientViewResource(TemplateResource templateResource) {
        this.templateResource = templateResource;
    }

    @PostConstruct
    public void init() {
        templateResource.loadTemplateJson(MODULE_NAME, "patient-details", "views/static/patient/json/patient-details.json",
                false);
        templateResource.loadTemplateJson(MODULE_NAME, "patient-edit", "views/static/patient/json/patient-edit.json",
                true);
    }
}

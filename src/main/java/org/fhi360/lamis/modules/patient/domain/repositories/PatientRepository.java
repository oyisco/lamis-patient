package org.fhi360.lamis.modules.patient.domain.repositories;

import org.fhi360.lamis.modules.patient.domain.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}

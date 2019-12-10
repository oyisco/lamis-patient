package org.fhi360.lamis.modules.patient.domain.repositories;

import org.fhi360.lamis.modules.patient.domain.entities.Biometrics;
import org.fhi360.lamis.modules.patient.domain.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiometricRepository extends JpaRepository<Biometrics, String> {
    List<Biometrics> findByPatient(Patient patient);

    Boolean existsByPatient(Patient patient);
}

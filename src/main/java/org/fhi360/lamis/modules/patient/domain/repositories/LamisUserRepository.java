package org.fhi360.lamis.modules.patient.domain.repositories;

import org.fhi360.lamis.modules.patient.domain.entities.LamisUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LamisUserRepository extends JpaRepository<LamisUser, Long> {
}

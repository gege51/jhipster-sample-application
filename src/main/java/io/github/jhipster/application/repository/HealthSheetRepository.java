package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.HealthSheet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HealthSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthSheetRepository extends JpaRepository<HealthSheet, Long> {

}

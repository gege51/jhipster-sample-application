package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Criteria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Criteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Long> {

}

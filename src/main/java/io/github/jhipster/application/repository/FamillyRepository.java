package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Familly;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Familly entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamillyRepository extends JpaRepository<Familly, Long> {

}

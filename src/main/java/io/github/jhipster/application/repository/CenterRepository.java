package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Center;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Center entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {

}

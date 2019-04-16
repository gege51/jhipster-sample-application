package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Breeding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Breeding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreedingRepository extends JpaRepository<Breeding, Long> {

}

package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.BilanType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BilanType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BilanTypeRepository extends JpaRepository<BilanType, Long> {

}

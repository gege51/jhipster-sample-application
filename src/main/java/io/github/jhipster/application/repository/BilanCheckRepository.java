package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.BilanCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the BilanCheck entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BilanCheckRepository extends JpaRepository<BilanCheck, Long> {

    @Query(value = "select distinct bilan_check from BilanCheck bilan_check left join fetch bilan_check.bilanTypes left join fetch bilan_check.criteria",
        countQuery = "select count(distinct bilan_check) from BilanCheck bilan_check")
    Page<BilanCheck> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct bilan_check from BilanCheck bilan_check left join fetch bilan_check.bilanTypes left join fetch bilan_check.criteria")
    List<BilanCheck> findAllWithEagerRelationships();

    @Query("select bilan_check from BilanCheck bilan_check left join fetch bilan_check.bilanTypes left join fetch bilan_check.criteria where bilan_check.id =:id")
    Optional<BilanCheck> findOneWithEagerRelationships(@Param("id") Long id);

}

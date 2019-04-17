package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "select distinct location from Location location left join fetch location.centers left join fetch location.breedings left join fetch location.famillies",
        countQuery = "select count(distinct location) from Location location")
    Page<Location> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct location from Location location left join fetch location.centers left join fetch location.breedings left join fetch location.famillies")
    List<Location> findAllWithEagerRelationships();

    @Query("select location from Location location left join fetch location.centers left join fetch location.breedings left join fetch location.famillies where location.id =:id")
    Optional<Location> findOneWithEagerRelationships(@Param("id") Long id);

}

package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Dog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Dog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

    @Query(value = "select distinct dog from Dog dog left join fetch dog.dateBilans",
        countQuery = "select count(distinct dog) from Dog dog")
    Page<Dog> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct dog from Dog dog left join fetch dog.dateBilans")
    List<Dog> findAllWithEagerRelationships();

    @Query("select dog from Dog dog left join fetch dog.dateBilans where dog.id =:id")
    Optional<Dog> findOneWithEagerRelationships(@Param("id") Long id);

}

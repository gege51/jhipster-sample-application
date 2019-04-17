package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Dog;
import io.github.jhipster.application.repository.DogRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dog.
 */
@RestController
@RequestMapping("/api")
public class DogResource {

    private final Logger log = LoggerFactory.getLogger(DogResource.class);

    private static final String ENTITY_NAME = "dog";

    private final DogRepository dogRepository;

    public DogResource(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    /**
     * POST  /dogs : Create a new dog.
     *
     * @param dog the dog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dog, or with status 400 (Bad Request) if the dog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dogs")
    public ResponseEntity<Dog> createDog(@Valid @RequestBody Dog dog) throws URISyntaxException {
        log.debug("REST request to save Dog : {}", dog);
        if (dog.getId() != null) {
            throw new BadRequestAlertException("A new dog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dog result = dogRepository.save(dog);
        return ResponseEntity.created(new URI("/api/dogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dogs : Updates an existing dog.
     *
     * @param dog the dog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dog,
     * or with status 400 (Bad Request) if the dog is not valid,
     * or with status 500 (Internal Server Error) if the dog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dogs")
    public ResponseEntity<Dog> updateDog(@Valid @RequestBody Dog dog) throws URISyntaxException {
        log.debug("REST request to update Dog : {}", dog);
        if (dog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dog result = dogRepository.save(dog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dogs : get all the dogs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of dogs in body
     */
    @GetMapping("/dogs")
    public List<Dog> getAllDogs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Dogs");
        return dogRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /dogs/:id : get the "id" dog.
     *
     * @param id the id of the dog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dog, or with status 404 (Not Found)
     */
    @GetMapping("/dogs/{id}")
    public ResponseEntity<Dog> getDog(@PathVariable Long id) {
        log.debug("REST request to get Dog : {}", id);
        Optional<Dog> dog = dogRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(dog);
    }

    /**
     * DELETE  /dogs/:id : delete the "id" dog.
     *
     * @param id the id of the dog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dogs/{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable Long id) {
        log.debug("REST request to delete Dog : {}", id);
        dogRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Breeding;
import io.github.jhipster.application.repository.BreedingRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Breeding.
 */
@RestController
@RequestMapping("/api")
public class BreedingResource {

    private final Logger log = LoggerFactory.getLogger(BreedingResource.class);

    private static final String ENTITY_NAME = "breeding";

    private final BreedingRepository breedingRepository;

    public BreedingResource(BreedingRepository breedingRepository) {
        this.breedingRepository = breedingRepository;
    }

    /**
     * POST  /breedings : Create a new breeding.
     *
     * @param breeding the breeding to create
     * @return the ResponseEntity with status 201 (Created) and with body the new breeding, or with status 400 (Bad Request) if the breeding has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/breedings")
    public ResponseEntity<Breeding> createBreeding(@RequestBody Breeding breeding) throws URISyntaxException {
        log.debug("REST request to save Breeding : {}", breeding);
        if (breeding.getId() != null) {
            throw new BadRequestAlertException("A new breeding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Breeding result = breedingRepository.save(breeding);
        return ResponseEntity.created(new URI("/api/breedings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /breedings : Updates an existing breeding.
     *
     * @param breeding the breeding to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated breeding,
     * or with status 400 (Bad Request) if the breeding is not valid,
     * or with status 500 (Internal Server Error) if the breeding couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/breedings")
    public ResponseEntity<Breeding> updateBreeding(@RequestBody Breeding breeding) throws URISyntaxException {
        log.debug("REST request to update Breeding : {}", breeding);
        if (breeding.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Breeding result = breedingRepository.save(breeding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, breeding.getId().toString()))
            .body(result);
    }

    /**
     * GET  /breedings : get all the breedings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of breedings in body
     */
    @GetMapping("/breedings")
    public List<Breeding> getAllBreedings() {
        log.debug("REST request to get all Breedings");
        return breedingRepository.findAll();
    }

    /**
     * GET  /breedings/:id : get the "id" breeding.
     *
     * @param id the id of the breeding to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the breeding, or with status 404 (Not Found)
     */
    @GetMapping("/breedings/{id}")
    public ResponseEntity<Breeding> getBreeding(@PathVariable Long id) {
        log.debug("REST request to get Breeding : {}", id);
        Optional<Breeding> breeding = breedingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(breeding);
    }

    /**
     * DELETE  /breedings/:id : delete the "id" breeding.
     *
     * @param id the id of the breeding to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/breedings/{id}")
    public ResponseEntity<Void> deleteBreeding(@PathVariable Long id) {
        log.debug("REST request to delete Breeding : {}", id);
        breedingRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

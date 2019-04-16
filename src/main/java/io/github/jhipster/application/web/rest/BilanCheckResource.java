package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.BilanCheck;
import io.github.jhipster.application.repository.BilanCheckRepository;
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
 * REST controller for managing BilanCheck.
 */
@RestController
@RequestMapping("/api")
public class BilanCheckResource {

    private final Logger log = LoggerFactory.getLogger(BilanCheckResource.class);

    private static final String ENTITY_NAME = "bilanCheck";

    private final BilanCheckRepository bilanCheckRepository;

    public BilanCheckResource(BilanCheckRepository bilanCheckRepository) {
        this.bilanCheckRepository = bilanCheckRepository;
    }

    /**
     * POST  /bilan-checks : Create a new bilanCheck.
     *
     * @param bilanCheck the bilanCheck to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bilanCheck, or with status 400 (Bad Request) if the bilanCheck has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bilan-checks")
    public ResponseEntity<BilanCheck> createBilanCheck(@RequestBody BilanCheck bilanCheck) throws URISyntaxException {
        log.debug("REST request to save BilanCheck : {}", bilanCheck);
        if (bilanCheck.getId() != null) {
            throw new BadRequestAlertException("A new bilanCheck cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BilanCheck result = bilanCheckRepository.save(bilanCheck);
        return ResponseEntity.created(new URI("/api/bilan-checks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bilan-checks : Updates an existing bilanCheck.
     *
     * @param bilanCheck the bilanCheck to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bilanCheck,
     * or with status 400 (Bad Request) if the bilanCheck is not valid,
     * or with status 500 (Internal Server Error) if the bilanCheck couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bilan-checks")
    public ResponseEntity<BilanCheck> updateBilanCheck(@RequestBody BilanCheck bilanCheck) throws URISyntaxException {
        log.debug("REST request to update BilanCheck : {}", bilanCheck);
        if (bilanCheck.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BilanCheck result = bilanCheckRepository.save(bilanCheck);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bilanCheck.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bilan-checks : get all the bilanChecks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of bilanChecks in body
     */
    @GetMapping("/bilan-checks")
    public List<BilanCheck> getAllBilanChecks(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all BilanChecks");
        return bilanCheckRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /bilan-checks/:id : get the "id" bilanCheck.
     *
     * @param id the id of the bilanCheck to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bilanCheck, or with status 404 (Not Found)
     */
    @GetMapping("/bilan-checks/{id}")
    public ResponseEntity<BilanCheck> getBilanCheck(@PathVariable Long id) {
        log.debug("REST request to get BilanCheck : {}", id);
        Optional<BilanCheck> bilanCheck = bilanCheckRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(bilanCheck);
    }

    /**
     * DELETE  /bilan-checks/:id : delete the "id" bilanCheck.
     *
     * @param id the id of the bilanCheck to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bilan-checks/{id}")
    public ResponseEntity<Void> deleteBilanCheck(@PathVariable Long id) {
        log.debug("REST request to delete BilanCheck : {}", id);
        bilanCheckRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

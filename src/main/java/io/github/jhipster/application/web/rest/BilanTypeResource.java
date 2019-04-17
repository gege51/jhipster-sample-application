package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.BilanType;
import io.github.jhipster.application.repository.BilanTypeRepository;
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
 * REST controller for managing BilanType.
 */
@RestController
@RequestMapping("/api")
public class BilanTypeResource {

    private final Logger log = LoggerFactory.getLogger(BilanTypeResource.class);

    private static final String ENTITY_NAME = "bilanType";

    private final BilanTypeRepository bilanTypeRepository;

    public BilanTypeResource(BilanTypeRepository bilanTypeRepository) {
        this.bilanTypeRepository = bilanTypeRepository;
    }

    /**
     * POST  /bilan-types : Create a new bilanType.
     *
     * @param bilanType the bilanType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bilanType, or with status 400 (Bad Request) if the bilanType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bilan-types")
    public ResponseEntity<BilanType> createBilanType(@RequestBody BilanType bilanType) throws URISyntaxException {
        log.debug("REST request to save BilanType : {}", bilanType);
        if (bilanType.getId() != null) {
            throw new BadRequestAlertException("A new bilanType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BilanType result = bilanTypeRepository.save(bilanType);
        return ResponseEntity.created(new URI("/api/bilan-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bilan-types : Updates an existing bilanType.
     *
     * @param bilanType the bilanType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bilanType,
     * or with status 400 (Bad Request) if the bilanType is not valid,
     * or with status 500 (Internal Server Error) if the bilanType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bilan-types")
    public ResponseEntity<BilanType> updateBilanType(@RequestBody BilanType bilanType) throws URISyntaxException {
        log.debug("REST request to update BilanType : {}", bilanType);
        if (bilanType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BilanType result = bilanTypeRepository.save(bilanType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bilanType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bilan-types : get all the bilanTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bilanTypes in body
     */
    @GetMapping("/bilan-types")
    public List<BilanType> getAllBilanTypes() {
        log.debug("REST request to get all BilanTypes");
        return bilanTypeRepository.findAll();
    }

    /**
     * GET  /bilan-types/:id : get the "id" bilanType.
     *
     * @param id the id of the bilanType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bilanType, or with status 404 (Not Found)
     */
    @GetMapping("/bilan-types/{id}")
    public ResponseEntity<BilanType> getBilanType(@PathVariable Long id) {
        log.debug("REST request to get BilanType : {}", id);
        Optional<BilanType> bilanType = bilanTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bilanType);
    }

    /**
     * DELETE  /bilan-types/:id : delete the "id" bilanType.
     *
     * @param id the id of the bilanType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bilan-types/{id}")
    public ResponseEntity<Void> deleteBilanType(@PathVariable Long id) {
        log.debug("REST request to delete BilanType : {}", id);
        bilanTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

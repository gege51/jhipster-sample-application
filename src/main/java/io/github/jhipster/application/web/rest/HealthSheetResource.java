package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.HealthSheet;
import io.github.jhipster.application.repository.HealthSheetRepository;
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
 * REST controller for managing HealthSheet.
 */
@RestController
@RequestMapping("/api")
public class HealthSheetResource {

    private final Logger log = LoggerFactory.getLogger(HealthSheetResource.class);

    private static final String ENTITY_NAME = "healthSheet";

    private final HealthSheetRepository healthSheetRepository;

    public HealthSheetResource(HealthSheetRepository healthSheetRepository) {
        this.healthSheetRepository = healthSheetRepository;
    }

    /**
     * POST  /health-sheets : Create a new healthSheet.
     *
     * @param healthSheet the healthSheet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new healthSheet, or with status 400 (Bad Request) if the healthSheet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/health-sheets")
    public ResponseEntity<HealthSheet> createHealthSheet(@RequestBody HealthSheet healthSheet) throws URISyntaxException {
        log.debug("REST request to save HealthSheet : {}", healthSheet);
        if (healthSheet.getId() != null) {
            throw new BadRequestAlertException("A new healthSheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HealthSheet result = healthSheetRepository.save(healthSheet);
        return ResponseEntity.created(new URI("/api/health-sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /health-sheets : Updates an existing healthSheet.
     *
     * @param healthSheet the healthSheet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated healthSheet,
     * or with status 400 (Bad Request) if the healthSheet is not valid,
     * or with status 500 (Internal Server Error) if the healthSheet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/health-sheets")
    public ResponseEntity<HealthSheet> updateHealthSheet(@RequestBody HealthSheet healthSheet) throws URISyntaxException {
        log.debug("REST request to update HealthSheet : {}", healthSheet);
        if (healthSheet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HealthSheet result = healthSheetRepository.save(healthSheet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, healthSheet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /health-sheets : get all the healthSheets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of healthSheets in body
     */
    @GetMapping("/health-sheets")
    public List<HealthSheet> getAllHealthSheets() {
        log.debug("REST request to get all HealthSheets");
        return healthSheetRepository.findAll();
    }

    /**
     * GET  /health-sheets/:id : get the "id" healthSheet.
     *
     * @param id the id of the healthSheet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the healthSheet, or with status 404 (Not Found)
     */
    @GetMapping("/health-sheets/{id}")
    public ResponseEntity<HealthSheet> getHealthSheet(@PathVariable Long id) {
        log.debug("REST request to get HealthSheet : {}", id);
        Optional<HealthSheet> healthSheet = healthSheetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(healthSheet);
    }

    /**
     * DELETE  /health-sheets/:id : delete the "id" healthSheet.
     *
     * @param id the id of the healthSheet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/health-sheets/{id}")
    public ResponseEntity<Void> deleteHealthSheet(@PathVariable Long id) {
        log.debug("REST request to delete HealthSheet : {}", id);
        healthSheetRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Center;
import io.github.jhipster.application.repository.CenterRepository;
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
 * REST controller for managing Center.
 */
@RestController
@RequestMapping("/api")
public class CenterResource {

    private final Logger log = LoggerFactory.getLogger(CenterResource.class);

    private static final String ENTITY_NAME = "center";

    private final CenterRepository centerRepository;

    public CenterResource(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    /**
     * POST  /centers : Create a new center.
     *
     * @param center the center to create
     * @return the ResponseEntity with status 201 (Created) and with body the new center, or with status 400 (Bad Request) if the center has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/centers")
    public ResponseEntity<Center> createCenter(@RequestBody Center center) throws URISyntaxException {
        log.debug("REST request to save Center : {}", center);
        if (center.getId() != null) {
            throw new BadRequestAlertException("A new center cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Center result = centerRepository.save(center);
        return ResponseEntity.created(new URI("/api/centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /centers : Updates an existing center.
     *
     * @param center the center to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated center,
     * or with status 400 (Bad Request) if the center is not valid,
     * or with status 500 (Internal Server Error) if the center couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/centers")
    public ResponseEntity<Center> updateCenter(@RequestBody Center center) throws URISyntaxException {
        log.debug("REST request to update Center : {}", center);
        if (center.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Center result = centerRepository.save(center);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, center.getId().toString()))
            .body(result);
    }

    /**
     * GET  /centers : get all the centers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of centers in body
     */
    @GetMapping("/centers")
    public List<Center> getAllCenters() {
        log.debug("REST request to get all Centers");
        return centerRepository.findAll();
    }

    /**
     * GET  /centers/:id : get the "id" center.
     *
     * @param id the id of the center to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the center, or with status 404 (Not Found)
     */
    @GetMapping("/centers/{id}")
    public ResponseEntity<Center> getCenter(@PathVariable Long id) {
        log.debug("REST request to get Center : {}", id);
        Optional<Center> center = centerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(center);
    }

    /**
     * DELETE  /centers/:id : delete the "id" center.
     *
     * @param id the id of the center to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/centers/{id}")
    public ResponseEntity<Void> deleteCenter(@PathVariable Long id) {
        log.debug("REST request to delete Center : {}", id);
        centerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

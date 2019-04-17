package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Familly;
import io.github.jhipster.application.repository.FamillyRepository;
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
 * REST controller for managing Familly.
 */
@RestController
@RequestMapping("/api")
public class FamillyResource {

    private final Logger log = LoggerFactory.getLogger(FamillyResource.class);

    private static final String ENTITY_NAME = "familly";

    private final FamillyRepository famillyRepository;

    public FamillyResource(FamillyRepository famillyRepository) {
        this.famillyRepository = famillyRepository;
    }

    /**
     * POST  /famillies : Create a new familly.
     *
     * @param familly the familly to create
     * @return the ResponseEntity with status 201 (Created) and with body the new familly, or with status 400 (Bad Request) if the familly has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/famillies")
    public ResponseEntity<Familly> createFamilly(@RequestBody Familly familly) throws URISyntaxException {
        log.debug("REST request to save Familly : {}", familly);
        if (familly.getId() != null) {
            throw new BadRequestAlertException("A new familly cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Familly result = famillyRepository.save(familly);
        return ResponseEntity.created(new URI("/api/famillies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /famillies : Updates an existing familly.
     *
     * @param familly the familly to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated familly,
     * or with status 400 (Bad Request) if the familly is not valid,
     * or with status 500 (Internal Server Error) if the familly couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/famillies")
    public ResponseEntity<Familly> updateFamilly(@RequestBody Familly familly) throws URISyntaxException {
        log.debug("REST request to update Familly : {}", familly);
        if (familly.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Familly result = famillyRepository.save(familly);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, familly.getId().toString()))
            .body(result);
    }

    /**
     * GET  /famillies : get all the famillies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of famillies in body
     */
    @GetMapping("/famillies")
    public List<Familly> getAllFamillies() {
        log.debug("REST request to get all Famillies");
        return famillyRepository.findAll();
    }

    /**
     * GET  /famillies/:id : get the "id" familly.
     *
     * @param id the id of the familly to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the familly, or with status 404 (Not Found)
     */
    @GetMapping("/famillies/{id}")
    public ResponseEntity<Familly> getFamilly(@PathVariable Long id) {
        log.debug("REST request to get Familly : {}", id);
        Optional<Familly> familly = famillyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(familly);
    }

    /**
     * DELETE  /famillies/:id : delete the "id" familly.
     *
     * @param id the id of the familly to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/famillies/{id}")
    public ResponseEntity<Void> deleteFamilly(@PathVariable Long id) {
        log.debug("REST request to delete Familly : {}", id);
        famillyRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

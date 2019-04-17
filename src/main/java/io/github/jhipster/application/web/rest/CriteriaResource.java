package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Criteria;
import io.github.jhipster.application.repository.CriteriaRepository;
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
 * REST controller for managing Criteria.
 */
@RestController
@RequestMapping("/api")
public class CriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CriteriaResource.class);

    private static final String ENTITY_NAME = "criteria";

    private final CriteriaRepository criteriaRepository;

    public CriteriaResource(CriteriaRepository criteriaRepository) {
        this.criteriaRepository = criteriaRepository;
    }

    /**
     * POST  /criteria : Create a new criteria.
     *
     * @param criteria the criteria to create
     * @return the ResponseEntity with status 201 (Created) and with body the new criteria, or with status 400 (Bad Request) if the criteria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/criteria")
    public ResponseEntity<Criteria> createCriteria(@RequestBody Criteria criteria) throws URISyntaxException {
        log.debug("REST request to save Criteria : {}", criteria);
        if (criteria.getId() != null) {
            throw new BadRequestAlertException("A new criteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Criteria result = criteriaRepository.save(criteria);
        return ResponseEntity.created(new URI("/api/criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /criteria : Updates an existing criteria.
     *
     * @param criteria the criteria to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated criteria,
     * or with status 400 (Bad Request) if the criteria is not valid,
     * or with status 500 (Internal Server Error) if the criteria couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/criteria")
    public ResponseEntity<Criteria> updateCriteria(@RequestBody Criteria criteria) throws URISyntaxException {
        log.debug("REST request to update Criteria : {}", criteria);
        if (criteria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Criteria result = criteriaRepository.save(criteria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, criteria.getId().toString()))
            .body(result);
    }

    /**
     * GET  /criteria : get all the criteria.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of criteria in body
     */
    @GetMapping("/criteria")
    public List<Criteria> getAllCriteria() {
        log.debug("REST request to get all Criteria");
        return criteriaRepository.findAll();
    }

    /**
     * GET  /criteria/:id : get the "id" criteria.
     *
     * @param id the id of the criteria to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the criteria, or with status 404 (Not Found)
     */
    @GetMapping("/criteria/{id}")
    public ResponseEntity<Criteria> getCriteria(@PathVariable Long id) {
        log.debug("REST request to get Criteria : {}", id);
        Optional<Criteria> criteria = criteriaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(criteria);
    }

    /**
     * DELETE  /criteria/:id : delete the "id" criteria.
     *
     * @param id the id of the criteria to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/criteria/{id}")
    public ResponseEntity<Void> deleteCriteria(@PathVariable Long id) {
        log.debug("REST request to delete Criteria : {}", id);
        criteriaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

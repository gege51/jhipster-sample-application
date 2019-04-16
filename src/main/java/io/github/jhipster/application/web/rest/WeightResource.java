package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Weight;
import io.github.jhipster.application.repository.WeightRepository;
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
 * REST controller for managing Weight.
 */
@RestController
@RequestMapping("/api")
public class WeightResource {

    private final Logger log = LoggerFactory.getLogger(WeightResource.class);

    private static final String ENTITY_NAME = "weight";

    private final WeightRepository weightRepository;

    public WeightResource(WeightRepository weightRepository) {
        this.weightRepository = weightRepository;
    }

    /**
     * POST  /weights : Create a new weight.
     *
     * @param weight the weight to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weight, or with status 400 (Bad Request) if the weight has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/weights")
    public ResponseEntity<Weight> createWeight(@RequestBody Weight weight) throws URISyntaxException {
        log.debug("REST request to save Weight : {}", weight);
        if (weight.getId() != null) {
            throw new BadRequestAlertException("A new weight cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Weight result = weightRepository.save(weight);
        return ResponseEntity.created(new URI("/api/weights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weights : Updates an existing weight.
     *
     * @param weight the weight to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weight,
     * or with status 400 (Bad Request) if the weight is not valid,
     * or with status 500 (Internal Server Error) if the weight couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/weights")
    public ResponseEntity<Weight> updateWeight(@RequestBody Weight weight) throws URISyntaxException {
        log.debug("REST request to update Weight : {}", weight);
        if (weight.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Weight result = weightRepository.save(weight);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weight.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weights : get all the weights.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of weights in body
     */
    @GetMapping("/weights")
    public List<Weight> getAllWeights() {
        log.debug("REST request to get all Weights");
        return weightRepository.findAll();
    }

    /**
     * GET  /weights/:id : get the "id" weight.
     *
     * @param id the id of the weight to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weight, or with status 404 (Not Found)
     */
    @GetMapping("/weights/{id}")
    public ResponseEntity<Weight> getWeight(@PathVariable Long id) {
        log.debug("REST request to get Weight : {}", id);
        Optional<Weight> weight = weightRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(weight);
    }

    /**
     * DELETE  /weights/:id : delete the "id" weight.
     *
     * @param id the id of the weight to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weights/{id}")
    public ResponseEntity<Void> deleteWeight(@PathVariable Long id) {
        log.debug("REST request to delete Weight : {}", id);
        weightRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

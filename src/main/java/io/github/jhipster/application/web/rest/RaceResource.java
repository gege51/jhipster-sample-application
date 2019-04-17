package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Race;
import io.github.jhipster.application.repository.RaceRepository;
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
 * REST controller for managing Race.
 */
@RestController
@RequestMapping("/api")
public class RaceResource {

    private final Logger log = LoggerFactory.getLogger(RaceResource.class);

    private static final String ENTITY_NAME = "race";

    private final RaceRepository raceRepository;

    public RaceResource(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    /**
     * POST  /races : Create a new race.
     *
     * @param race the race to create
     * @return the ResponseEntity with status 201 (Created) and with body the new race, or with status 400 (Bad Request) if the race has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/races")
    public ResponseEntity<Race> createRace(@RequestBody Race race) throws URISyntaxException {
        log.debug("REST request to save Race : {}", race);
        if (race.getId() != null) {
            throw new BadRequestAlertException("A new race cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Race result = raceRepository.save(race);
        return ResponseEntity.created(new URI("/api/races/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /races : Updates an existing race.
     *
     * @param race the race to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated race,
     * or with status 400 (Bad Request) if the race is not valid,
     * or with status 500 (Internal Server Error) if the race couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/races")
    public ResponseEntity<Race> updateRace(@RequestBody Race race) throws URISyntaxException {
        log.debug("REST request to update Race : {}", race);
        if (race.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Race result = raceRepository.save(race);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, race.getId().toString()))
            .body(result);
    }

    /**
     * GET  /races : get all the races.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of races in body
     */
    @GetMapping("/races")
    public List<Race> getAllRaces() {
        log.debug("REST request to get all Races");
        return raceRepository.findAll();
    }

    /**
     * GET  /races/:id : get the "id" race.
     *
     * @param id the id of the race to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the race, or with status 404 (Not Found)
     */
    @GetMapping("/races/{id}")
    public ResponseEntity<Race> getRace(@PathVariable Long id) {
        log.debug("REST request to get Race : {}", id);
        Optional<Race> race = raceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(race);
    }

    /**
     * DELETE  /races/:id : delete the "id" race.
     *
     * @param id the id of the race to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/races/{id}")
    public ResponseEntity<Void> deleteRace(@PathVariable Long id) {
        log.debug("REST request to delete Race : {}", id);
        raceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

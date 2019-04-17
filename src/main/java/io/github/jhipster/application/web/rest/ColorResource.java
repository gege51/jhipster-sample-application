package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Color;
import io.github.jhipster.application.repository.ColorRepository;
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
 * REST controller for managing Color.
 */
@RestController
@RequestMapping("/api")
public class ColorResource {

    private final Logger log = LoggerFactory.getLogger(ColorResource.class);

    private static final String ENTITY_NAME = "color";

    private final ColorRepository colorRepository;

    public ColorResource(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    /**
     * POST  /colors : Create a new color.
     *
     * @param color the color to create
     * @return the ResponseEntity with status 201 (Created) and with body the new color, or with status 400 (Bad Request) if the color has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/colors")
    public ResponseEntity<Color> createColor(@RequestBody Color color) throws URISyntaxException {
        log.debug("REST request to save Color : {}", color);
        if (color.getId() != null) {
            throw new BadRequestAlertException("A new color cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Color result = colorRepository.save(color);
        return ResponseEntity.created(new URI("/api/colors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /colors : Updates an existing color.
     *
     * @param color the color to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated color,
     * or with status 400 (Bad Request) if the color is not valid,
     * or with status 500 (Internal Server Error) if the color couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/colors")
    public ResponseEntity<Color> updateColor(@RequestBody Color color) throws URISyntaxException {
        log.debug("REST request to update Color : {}", color);
        if (color.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Color result = colorRepository.save(color);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, color.getId().toString()))
            .body(result);
    }

    /**
     * GET  /colors : get all the colors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of colors in body
     */
    @GetMapping("/colors")
    public List<Color> getAllColors() {
        log.debug("REST request to get all Colors");
        return colorRepository.findAll();
    }

    /**
     * GET  /colors/:id : get the "id" color.
     *
     * @param id the id of the color to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the color, or with status 404 (Not Found)
     */
    @GetMapping("/colors/{id}")
    public ResponseEntity<Color> getColor(@PathVariable Long id) {
        log.debug("REST request to get Color : {}", id);
        Optional<Color> color = colorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(color);
    }

    /**
     * DELETE  /colors/:id : delete the "id" color.
     *
     * @param id the id of the color to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/colors/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        log.debug("REST request to delete Color : {}", id);
        colorRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

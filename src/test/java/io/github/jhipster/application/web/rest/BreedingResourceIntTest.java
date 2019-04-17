package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Breeding;
import io.github.jhipster.application.repository.BreedingRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BreedingResource REST controller.
 *
 * @see BreedingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class BreedingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BreedingRepository breedingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBreedingMockMvc;

    private Breeding breeding;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BreedingResource breedingResource = new BreedingResource(breedingRepository);
        this.restBreedingMockMvc = MockMvcBuilders.standaloneSetup(breedingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Breeding createEntity(EntityManager em) {
        Breeding breeding = new Breeding()
            .name(DEFAULT_NAME);
        return breeding;
    }

    @Before
    public void initTest() {
        breeding = createEntity(em);
    }

    @Test
    @Transactional
    public void createBreeding() throws Exception {
        int databaseSizeBeforeCreate = breedingRepository.findAll().size();

        // Create the Breeding
        restBreedingMockMvc.perform(post("/api/breedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breeding)))
            .andExpect(status().isCreated());

        // Validate the Breeding in the database
        List<Breeding> breedingList = breedingRepository.findAll();
        assertThat(breedingList).hasSize(databaseSizeBeforeCreate + 1);
        Breeding testBreeding = breedingList.get(breedingList.size() - 1);
        assertThat(testBreeding.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBreedingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = breedingRepository.findAll().size();

        // Create the Breeding with an existing ID
        breeding.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBreedingMockMvc.perform(post("/api/breedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breeding)))
            .andExpect(status().isBadRequest());

        // Validate the Breeding in the database
        List<Breeding> breedingList = breedingRepository.findAll();
        assertThat(breedingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBreedings() throws Exception {
        // Initialize the database
        breedingRepository.saveAndFlush(breeding);

        // Get all the breedingList
        restBreedingMockMvc.perform(get("/api/breedings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(breeding.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getBreeding() throws Exception {
        // Initialize the database
        breedingRepository.saveAndFlush(breeding);

        // Get the breeding
        restBreedingMockMvc.perform(get("/api/breedings/{id}", breeding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(breeding.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBreeding() throws Exception {
        // Get the breeding
        restBreedingMockMvc.perform(get("/api/breedings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBreeding() throws Exception {
        // Initialize the database
        breedingRepository.saveAndFlush(breeding);

        int databaseSizeBeforeUpdate = breedingRepository.findAll().size();

        // Update the breeding
        Breeding updatedBreeding = breedingRepository.findById(breeding.getId()).get();
        // Disconnect from session so that the updates on updatedBreeding are not directly saved in db
        em.detach(updatedBreeding);
        updatedBreeding
            .name(UPDATED_NAME);

        restBreedingMockMvc.perform(put("/api/breedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBreeding)))
            .andExpect(status().isOk());

        // Validate the Breeding in the database
        List<Breeding> breedingList = breedingRepository.findAll();
        assertThat(breedingList).hasSize(databaseSizeBeforeUpdate);
        Breeding testBreeding = breedingList.get(breedingList.size() - 1);
        assertThat(testBreeding.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBreeding() throws Exception {
        int databaseSizeBeforeUpdate = breedingRepository.findAll().size();

        // Create the Breeding

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBreedingMockMvc.perform(put("/api/breedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(breeding)))
            .andExpect(status().isBadRequest());

        // Validate the Breeding in the database
        List<Breeding> breedingList = breedingRepository.findAll();
        assertThat(breedingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBreeding() throws Exception {
        // Initialize the database
        breedingRepository.saveAndFlush(breeding);

        int databaseSizeBeforeDelete = breedingRepository.findAll().size();

        // Delete the breeding
        restBreedingMockMvc.perform(delete("/api/breedings/{id}", breeding.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Breeding> breedingList = breedingRepository.findAll();
        assertThat(breedingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Breeding.class);
        Breeding breeding1 = new Breeding();
        breeding1.setId(1L);
        Breeding breeding2 = new Breeding();
        breeding2.setId(breeding1.getId());
        assertThat(breeding1).isEqualTo(breeding2);
        breeding2.setId(2L);
        assertThat(breeding1).isNotEqualTo(breeding2);
        breeding1.setId(null);
        assertThat(breeding1).isNotEqualTo(breeding2);
    }
}

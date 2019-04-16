package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Criteria;
import io.github.jhipster.application.repository.CriteriaRepository;
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
 * Test class for the CriteriaResource REST controller.
 *
 * @see CriteriaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class CriteriaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private CriteriaRepository criteriaRepository;

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

    private MockMvc restCriteriaMockMvc;

    private Criteria criteria;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CriteriaResource criteriaResource = new CriteriaResource(criteriaRepository);
        this.restCriteriaMockMvc = MockMvcBuilders.standaloneSetup(criteriaResource)
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
    public static Criteria createEntity(EntityManager em) {
        Criteria criteria = new Criteria()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .weight(DEFAULT_WEIGHT)
            .value(DEFAULT_VALUE)
            .comment(DEFAULT_COMMENT);
        return criteria;
    }

    @Before
    public void initTest() {
        criteria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCriteria() throws Exception {
        int databaseSizeBeforeCreate = criteriaRepository.findAll().size();

        // Create the Criteria
        restCriteriaMockMvc.perform(post("/api/criteria")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criteria)))
            .andExpect(status().isCreated());

        // Validate the Criteria in the database
        List<Criteria> criteriaList = criteriaRepository.findAll();
        assertThat(criteriaList).hasSize(databaseSizeBeforeCreate + 1);
        Criteria testCriteria = criteriaList.get(criteriaList.size() - 1);
        assertThat(testCriteria.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCriteria.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCriteria.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCriteria.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCriteria.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createCriteriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = criteriaRepository.findAll().size();

        // Create the Criteria with an existing ID
        criteria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCriteriaMockMvc.perform(post("/api/criteria")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criteria)))
            .andExpect(status().isBadRequest());

        // Validate the Criteria in the database
        List<Criteria> criteriaList = criteriaRepository.findAll();
        assertThat(criteriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCriteria() throws Exception {
        // Initialize the database
        criteriaRepository.saveAndFlush(criteria);

        // Get all the criteriaList
        restCriteriaMockMvc.perform(get("/api/criteria?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(criteria.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getCriteria() throws Exception {
        // Initialize the database
        criteriaRepository.saveAndFlush(criteria);

        // Get the criteria
        restCriteriaMockMvc.perform(get("/api/criteria/{id}", criteria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(criteria.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCriteria() throws Exception {
        // Get the criteria
        restCriteriaMockMvc.perform(get("/api/criteria/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCriteria() throws Exception {
        // Initialize the database
        criteriaRepository.saveAndFlush(criteria);

        int databaseSizeBeforeUpdate = criteriaRepository.findAll().size();

        // Update the criteria
        Criteria updatedCriteria = criteriaRepository.findById(criteria.getId()).get();
        // Disconnect from session so that the updates on updatedCriteria are not directly saved in db
        em.detach(updatedCriteria);
        updatedCriteria
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .weight(UPDATED_WEIGHT)
            .value(UPDATED_VALUE)
            .comment(UPDATED_COMMENT);

        restCriteriaMockMvc.perform(put("/api/criteria")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCriteria)))
            .andExpect(status().isOk());

        // Validate the Criteria in the database
        List<Criteria> criteriaList = criteriaRepository.findAll();
        assertThat(criteriaList).hasSize(databaseSizeBeforeUpdate);
        Criteria testCriteria = criteriaList.get(criteriaList.size() - 1);
        assertThat(testCriteria.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCriteria.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCriteria.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCriteria.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCriteria.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingCriteria() throws Exception {
        int databaseSizeBeforeUpdate = criteriaRepository.findAll().size();

        // Create the Criteria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCriteriaMockMvc.perform(put("/api/criteria")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(criteria)))
            .andExpect(status().isBadRequest());

        // Validate the Criteria in the database
        List<Criteria> criteriaList = criteriaRepository.findAll();
        assertThat(criteriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCriteria() throws Exception {
        // Initialize the database
        criteriaRepository.saveAndFlush(criteria);

        int databaseSizeBeforeDelete = criteriaRepository.findAll().size();

        // Delete the criteria
        restCriteriaMockMvc.perform(delete("/api/criteria/{id}", criteria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Criteria> criteriaList = criteriaRepository.findAll();
        assertThat(criteriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Criteria.class);
        Criteria criteria1 = new Criteria();
        criteria1.setId(1L);
        Criteria criteria2 = new Criteria();
        criteria2.setId(criteria1.getId());
        assertThat(criteria1).isEqualTo(criteria2);
        criteria2.setId(2L);
        assertThat(criteria1).isNotEqualTo(criteria2);
        criteria1.setId(null);
        assertThat(criteria1).isNotEqualTo(criteria2);
    }
}

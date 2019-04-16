package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.HealthSheet;
import io.github.jhipster.application.repository.HealthSheetRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HealthSheetResource REST controller.
 *
 * @see HealthSheetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class HealthSheetResourceIntTest {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private HealthSheetRepository healthSheetRepository;

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

    private MockMvc restHealthSheetMockMvc;

    private HealthSheet healthSheet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HealthSheetResource healthSheetResource = new HealthSheetResource(healthSheetRepository);
        this.restHealthSheetMockMvc = MockMvcBuilders.standaloneSetup(healthSheetResource)
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
    public static HealthSheet createEntity(EntityManager em) {
        HealthSheet healthSheet = new HealthSheet()
            .date(DEFAULT_DATE)
            .reason(DEFAULT_REASON)
            .comment(DEFAULT_COMMENT);
        return healthSheet;
    }

    @Before
    public void initTest() {
        healthSheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createHealthSheet() throws Exception {
        int databaseSizeBeforeCreate = healthSheetRepository.findAll().size();

        // Create the HealthSheet
        restHealthSheetMockMvc.perform(post("/api/health-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthSheet)))
            .andExpect(status().isCreated());

        // Validate the HealthSheet in the database
        List<HealthSheet> healthSheetList = healthSheetRepository.findAll();
        assertThat(healthSheetList).hasSize(databaseSizeBeforeCreate + 1);
        HealthSheet testHealthSheet = healthSheetList.get(healthSheetList.size() - 1);
        assertThat(testHealthSheet.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testHealthSheet.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testHealthSheet.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createHealthSheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = healthSheetRepository.findAll().size();

        // Create the HealthSheet with an existing ID
        healthSheet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHealthSheetMockMvc.perform(post("/api/health-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthSheet)))
            .andExpect(status().isBadRequest());

        // Validate the HealthSheet in the database
        List<HealthSheet> healthSheetList = healthSheetRepository.findAll();
        assertThat(healthSheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHealthSheets() throws Exception {
        // Initialize the database
        healthSheetRepository.saveAndFlush(healthSheet);

        // Get all the healthSheetList
        restHealthSheetMockMvc.perform(get("/api/health-sheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getHealthSheet() throws Exception {
        // Initialize the database
        healthSheetRepository.saveAndFlush(healthSheet);

        // Get the healthSheet
        restHealthSheetMockMvc.perform(get("/api/health-sheets/{id}", healthSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(healthSheet.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHealthSheet() throws Exception {
        // Get the healthSheet
        restHealthSheetMockMvc.perform(get("/api/health-sheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHealthSheet() throws Exception {
        // Initialize the database
        healthSheetRepository.saveAndFlush(healthSheet);

        int databaseSizeBeforeUpdate = healthSheetRepository.findAll().size();

        // Update the healthSheet
        HealthSheet updatedHealthSheet = healthSheetRepository.findById(healthSheet.getId()).get();
        // Disconnect from session so that the updates on updatedHealthSheet are not directly saved in db
        em.detach(updatedHealthSheet);
        updatedHealthSheet
            .date(UPDATED_DATE)
            .reason(UPDATED_REASON)
            .comment(UPDATED_COMMENT);

        restHealthSheetMockMvc.perform(put("/api/health-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHealthSheet)))
            .andExpect(status().isOk());

        // Validate the HealthSheet in the database
        List<HealthSheet> healthSheetList = healthSheetRepository.findAll();
        assertThat(healthSheetList).hasSize(databaseSizeBeforeUpdate);
        HealthSheet testHealthSheet = healthSheetList.get(healthSheetList.size() - 1);
        assertThat(testHealthSheet.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHealthSheet.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testHealthSheet.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingHealthSheet() throws Exception {
        int databaseSizeBeforeUpdate = healthSheetRepository.findAll().size();

        // Create the HealthSheet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHealthSheetMockMvc.perform(put("/api/health-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthSheet)))
            .andExpect(status().isBadRequest());

        // Validate the HealthSheet in the database
        List<HealthSheet> healthSheetList = healthSheetRepository.findAll();
        assertThat(healthSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHealthSheet() throws Exception {
        // Initialize the database
        healthSheetRepository.saveAndFlush(healthSheet);

        int databaseSizeBeforeDelete = healthSheetRepository.findAll().size();

        // Delete the healthSheet
        restHealthSheetMockMvc.perform(delete("/api/health-sheets/{id}", healthSheet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HealthSheet> healthSheetList = healthSheetRepository.findAll();
        assertThat(healthSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthSheet.class);
        HealthSheet healthSheet1 = new HealthSheet();
        healthSheet1.setId(1L);
        HealthSheet healthSheet2 = new HealthSheet();
        healthSheet2.setId(healthSheet1.getId());
        assertThat(healthSheet1).isEqualTo(healthSheet2);
        healthSheet2.setId(2L);
        assertThat(healthSheet1).isNotEqualTo(healthSheet2);
        healthSheet1.setId(null);
        assertThat(healthSheet1).isNotEqualTo(healthSheet2);
    }
}

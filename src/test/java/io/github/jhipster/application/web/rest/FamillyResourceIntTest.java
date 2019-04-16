package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Familly;
import io.github.jhipster.application.repository.FamillyRepository;
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
 * Test class for the FamillyResource REST controller.
 *
 * @see FamillyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class FamillyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FamillyRepository famillyRepository;

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

    private MockMvc restFamillyMockMvc;

    private Familly familly;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamillyResource famillyResource = new FamillyResource(famillyRepository);
        this.restFamillyMockMvc = MockMvcBuilders.standaloneSetup(famillyResource)
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
    public static Familly createEntity(EntityManager em) {
        Familly familly = new Familly()
            .name(DEFAULT_NAME);
        return familly;
    }

    @Before
    public void initTest() {
        familly = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilly() throws Exception {
        int databaseSizeBeforeCreate = famillyRepository.findAll().size();

        // Create the Familly
        restFamillyMockMvc.perform(post("/api/famillies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familly)))
            .andExpect(status().isCreated());

        // Validate the Familly in the database
        List<Familly> famillyList = famillyRepository.findAll();
        assertThat(famillyList).hasSize(databaseSizeBeforeCreate + 1);
        Familly testFamilly = famillyList.get(famillyList.size() - 1);
        assertThat(testFamilly.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFamillyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = famillyRepository.findAll().size();

        // Create the Familly with an existing ID
        familly.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamillyMockMvc.perform(post("/api/famillies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familly)))
            .andExpect(status().isBadRequest());

        // Validate the Familly in the database
        List<Familly> famillyList = famillyRepository.findAll();
        assertThat(famillyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFamillies() throws Exception {
        // Initialize the database
        famillyRepository.saveAndFlush(familly);

        // Get all the famillyList
        restFamillyMockMvc.perform(get("/api/famillies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familly.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getFamilly() throws Exception {
        // Initialize the database
        famillyRepository.saveAndFlush(familly);

        // Get the familly
        restFamillyMockMvc.perform(get("/api/famillies/{id}", familly.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familly.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFamilly() throws Exception {
        // Get the familly
        restFamillyMockMvc.perform(get("/api/famillies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilly() throws Exception {
        // Initialize the database
        famillyRepository.saveAndFlush(familly);

        int databaseSizeBeforeUpdate = famillyRepository.findAll().size();

        // Update the familly
        Familly updatedFamilly = famillyRepository.findById(familly.getId()).get();
        // Disconnect from session so that the updates on updatedFamilly are not directly saved in db
        em.detach(updatedFamilly);
        updatedFamilly
            .name(UPDATED_NAME);

        restFamillyMockMvc.perform(put("/api/famillies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFamilly)))
            .andExpect(status().isOk());

        // Validate the Familly in the database
        List<Familly> famillyList = famillyRepository.findAll();
        assertThat(famillyList).hasSize(databaseSizeBeforeUpdate);
        Familly testFamilly = famillyList.get(famillyList.size() - 1);
        assertThat(testFamilly.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilly() throws Exception {
        int databaseSizeBeforeUpdate = famillyRepository.findAll().size();

        // Create the Familly

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamillyMockMvc.perform(put("/api/famillies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familly)))
            .andExpect(status().isBadRequest());

        // Validate the Familly in the database
        List<Familly> famillyList = famillyRepository.findAll();
        assertThat(famillyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFamilly() throws Exception {
        // Initialize the database
        famillyRepository.saveAndFlush(familly);

        int databaseSizeBeforeDelete = famillyRepository.findAll().size();

        // Delete the familly
        restFamillyMockMvc.perform(delete("/api/famillies/{id}", familly.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Familly> famillyList = famillyRepository.findAll();
        assertThat(famillyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Familly.class);
        Familly familly1 = new Familly();
        familly1.setId(1L);
        Familly familly2 = new Familly();
        familly2.setId(familly1.getId());
        assertThat(familly1).isEqualTo(familly2);
        familly2.setId(2L);
        assertThat(familly1).isNotEqualTo(familly2);
        familly1.setId(null);
        assertThat(familly1).isNotEqualTo(familly2);
    }
}

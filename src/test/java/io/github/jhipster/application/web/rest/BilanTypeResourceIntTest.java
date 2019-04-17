package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.BilanType;
import io.github.jhipster.application.repository.BilanTypeRepository;
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
 * Test class for the BilanTypeResource REST controller.
 *
 * @see BilanTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class BilanTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BilanTypeRepository bilanTypeRepository;

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

    private MockMvc restBilanTypeMockMvc;

    private BilanType bilanType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BilanTypeResource bilanTypeResource = new BilanTypeResource(bilanTypeRepository);
        this.restBilanTypeMockMvc = MockMvcBuilders.standaloneSetup(bilanTypeResource)
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
    public static BilanType createEntity(EntityManager em) {
        BilanType bilanType = new BilanType()
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME);
        return bilanType;
    }

    @Before
    public void initTest() {
        bilanType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBilanType() throws Exception {
        int databaseSizeBeforeCreate = bilanTypeRepository.findAll().size();

        // Create the BilanType
        restBilanTypeMockMvc.perform(post("/api/bilan-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bilanType)))
            .andExpect(status().isCreated());

        // Validate the BilanType in the database
        List<BilanType> bilanTypeList = bilanTypeRepository.findAll();
        assertThat(bilanTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BilanType testBilanType = bilanTypeList.get(bilanTypeList.size() - 1);
        assertThat(testBilanType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBilanType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBilanTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bilanTypeRepository.findAll().size();

        // Create the BilanType with an existing ID
        bilanType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBilanTypeMockMvc.perform(post("/api/bilan-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bilanType)))
            .andExpect(status().isBadRequest());

        // Validate the BilanType in the database
        List<BilanType> bilanTypeList = bilanTypeRepository.findAll();
        assertThat(bilanTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBilanTypes() throws Exception {
        // Initialize the database
        bilanTypeRepository.saveAndFlush(bilanType);

        // Get all the bilanTypeList
        restBilanTypeMockMvc.perform(get("/api/bilan-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bilanType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getBilanType() throws Exception {
        // Initialize the database
        bilanTypeRepository.saveAndFlush(bilanType);

        // Get the bilanType
        restBilanTypeMockMvc.perform(get("/api/bilan-types/{id}", bilanType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bilanType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBilanType() throws Exception {
        // Get the bilanType
        restBilanTypeMockMvc.perform(get("/api/bilan-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBilanType() throws Exception {
        // Initialize the database
        bilanTypeRepository.saveAndFlush(bilanType);

        int databaseSizeBeforeUpdate = bilanTypeRepository.findAll().size();

        // Update the bilanType
        BilanType updatedBilanType = bilanTypeRepository.findById(bilanType.getId()).get();
        // Disconnect from session so that the updates on updatedBilanType are not directly saved in db
        em.detach(updatedBilanType);
        updatedBilanType
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME);

        restBilanTypeMockMvc.perform(put("/api/bilan-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBilanType)))
            .andExpect(status().isOk());

        // Validate the BilanType in the database
        List<BilanType> bilanTypeList = bilanTypeRepository.findAll();
        assertThat(bilanTypeList).hasSize(databaseSizeBeforeUpdate);
        BilanType testBilanType = bilanTypeList.get(bilanTypeList.size() - 1);
        assertThat(testBilanType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBilanType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBilanType() throws Exception {
        int databaseSizeBeforeUpdate = bilanTypeRepository.findAll().size();

        // Create the BilanType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBilanTypeMockMvc.perform(put("/api/bilan-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bilanType)))
            .andExpect(status().isBadRequest());

        // Validate the BilanType in the database
        List<BilanType> bilanTypeList = bilanTypeRepository.findAll();
        assertThat(bilanTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBilanType() throws Exception {
        // Initialize the database
        bilanTypeRepository.saveAndFlush(bilanType);

        int databaseSizeBeforeDelete = bilanTypeRepository.findAll().size();

        // Delete the bilanType
        restBilanTypeMockMvc.perform(delete("/api/bilan-types/{id}", bilanType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BilanType> bilanTypeList = bilanTypeRepository.findAll();
        assertThat(bilanTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BilanType.class);
        BilanType bilanType1 = new BilanType();
        bilanType1.setId(1L);
        BilanType bilanType2 = new BilanType();
        bilanType2.setId(bilanType1.getId());
        assertThat(bilanType1).isEqualTo(bilanType2);
        bilanType2.setId(2L);
        assertThat(bilanType1).isNotEqualTo(bilanType2);
        bilanType1.setId(null);
        assertThat(bilanType1).isNotEqualTo(bilanType2);
    }
}

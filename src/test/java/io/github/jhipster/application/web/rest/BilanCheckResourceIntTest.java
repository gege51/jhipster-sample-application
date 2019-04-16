package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.BilanCheck;
import io.github.jhipster.application.repository.BilanCheckRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BilanCheckResource REST controller.
 *
 * @see BilanCheckResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class BilanCheckResourceIntTest {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BilanCheckRepository bilanCheckRepository;

    @Mock
    private BilanCheckRepository bilanCheckRepositoryMock;

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

    private MockMvc restBilanCheckMockMvc;

    private BilanCheck bilanCheck;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BilanCheckResource bilanCheckResource = new BilanCheckResource(bilanCheckRepository);
        this.restBilanCheckMockMvc = MockMvcBuilders.standaloneSetup(bilanCheckResource)
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
    public static BilanCheck createEntity(EntityManager em) {
        BilanCheck bilanCheck = new BilanCheck()
            .date(DEFAULT_DATE);
        return bilanCheck;
    }

    @Before
    public void initTest() {
        bilanCheck = createEntity(em);
    }

    @Test
    @Transactional
    public void createBilanCheck() throws Exception {
        int databaseSizeBeforeCreate = bilanCheckRepository.findAll().size();

        // Create the BilanCheck
        restBilanCheckMockMvc.perform(post("/api/bilan-checks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bilanCheck)))
            .andExpect(status().isCreated());

        // Validate the BilanCheck in the database
        List<BilanCheck> bilanCheckList = bilanCheckRepository.findAll();
        assertThat(bilanCheckList).hasSize(databaseSizeBeforeCreate + 1);
        BilanCheck testBilanCheck = bilanCheckList.get(bilanCheckList.size() - 1);
        assertThat(testBilanCheck.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createBilanCheckWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bilanCheckRepository.findAll().size();

        // Create the BilanCheck with an existing ID
        bilanCheck.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBilanCheckMockMvc.perform(post("/api/bilan-checks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bilanCheck)))
            .andExpect(status().isBadRequest());

        // Validate the BilanCheck in the database
        List<BilanCheck> bilanCheckList = bilanCheckRepository.findAll();
        assertThat(bilanCheckList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBilanChecks() throws Exception {
        // Initialize the database
        bilanCheckRepository.saveAndFlush(bilanCheck);

        // Get all the bilanCheckList
        restBilanCheckMockMvc.perform(get("/api/bilan-checks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bilanCheck.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBilanChecksWithEagerRelationshipsIsEnabled() throws Exception {
        BilanCheckResource bilanCheckResource = new BilanCheckResource(bilanCheckRepositoryMock);
        when(bilanCheckRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restBilanCheckMockMvc = MockMvcBuilders.standaloneSetup(bilanCheckResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBilanCheckMockMvc.perform(get("/api/bilan-checks?eagerload=true"))
        .andExpect(status().isOk());

        verify(bilanCheckRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBilanChecksWithEagerRelationshipsIsNotEnabled() throws Exception {
        BilanCheckResource bilanCheckResource = new BilanCheckResource(bilanCheckRepositoryMock);
            when(bilanCheckRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restBilanCheckMockMvc = MockMvcBuilders.standaloneSetup(bilanCheckResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBilanCheckMockMvc.perform(get("/api/bilan-checks?eagerload=true"))
        .andExpect(status().isOk());

            verify(bilanCheckRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBilanCheck() throws Exception {
        // Initialize the database
        bilanCheckRepository.saveAndFlush(bilanCheck);

        // Get the bilanCheck
        restBilanCheckMockMvc.perform(get("/api/bilan-checks/{id}", bilanCheck.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bilanCheck.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBilanCheck() throws Exception {
        // Get the bilanCheck
        restBilanCheckMockMvc.perform(get("/api/bilan-checks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBilanCheck() throws Exception {
        // Initialize the database
        bilanCheckRepository.saveAndFlush(bilanCheck);

        int databaseSizeBeforeUpdate = bilanCheckRepository.findAll().size();

        // Update the bilanCheck
        BilanCheck updatedBilanCheck = bilanCheckRepository.findById(bilanCheck.getId()).get();
        // Disconnect from session so that the updates on updatedBilanCheck are not directly saved in db
        em.detach(updatedBilanCheck);
        updatedBilanCheck
            .date(UPDATED_DATE);

        restBilanCheckMockMvc.perform(put("/api/bilan-checks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBilanCheck)))
            .andExpect(status().isOk());

        // Validate the BilanCheck in the database
        List<BilanCheck> bilanCheckList = bilanCheckRepository.findAll();
        assertThat(bilanCheckList).hasSize(databaseSizeBeforeUpdate);
        BilanCheck testBilanCheck = bilanCheckList.get(bilanCheckList.size() - 1);
        assertThat(testBilanCheck.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBilanCheck() throws Exception {
        int databaseSizeBeforeUpdate = bilanCheckRepository.findAll().size();

        // Create the BilanCheck

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBilanCheckMockMvc.perform(put("/api/bilan-checks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bilanCheck)))
            .andExpect(status().isBadRequest());

        // Validate the BilanCheck in the database
        List<BilanCheck> bilanCheckList = bilanCheckRepository.findAll();
        assertThat(bilanCheckList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBilanCheck() throws Exception {
        // Initialize the database
        bilanCheckRepository.saveAndFlush(bilanCheck);

        int databaseSizeBeforeDelete = bilanCheckRepository.findAll().size();

        // Delete the bilanCheck
        restBilanCheckMockMvc.perform(delete("/api/bilan-checks/{id}", bilanCheck.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BilanCheck> bilanCheckList = bilanCheckRepository.findAll();
        assertThat(bilanCheckList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BilanCheck.class);
        BilanCheck bilanCheck1 = new BilanCheck();
        bilanCheck1.setId(1L);
        BilanCheck bilanCheck2 = new BilanCheck();
        bilanCheck2.setId(bilanCheck1.getId());
        assertThat(bilanCheck1).isEqualTo(bilanCheck2);
        bilanCheck2.setId(2L);
        assertThat(bilanCheck1).isNotEqualTo(bilanCheck2);
        bilanCheck1.setId(null);
        assertThat(bilanCheck1).isNotEqualTo(bilanCheck2);
    }
}

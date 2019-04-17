package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Dog;
import io.github.jhipster.application.repository.DogRepository;
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
 * Test class for the DogResource REST controller.
 *
 * @see DogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DogResourceIntTest {

    private static final Integer DEFAULT_PUCE_ID = 1;
    private static final Integer UPDATED_PUCE_ID = 2;

    private static final String DEFAULT_TATOO_ID = "AAAAAAA";
    private static final String UPDATED_TATOO_ID = "BBBBBBB";

    private static final Integer DEFAULT_PASSPORT_ID = 1;
    private static final Integer UPDATED_PASSPORT_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_1 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_1 = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SEXE = "AAAAAAAAAA";
    private static final String UPDATED_SEXE = "BBBBBBBBBB";

    private static final String DEFAULT_HC_NUM = "AAAAAAAAAA";
    private static final String UPDATED_HC_NUM = "BBBBBBBBBB";

    @Autowired
    private DogRepository dogRepository;

    @Mock
    private DogRepository dogRepositoryMock;

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

    private MockMvc restDogMockMvc;

    private Dog dog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DogResource dogResource = new DogResource(dogRepository);
        this.restDogMockMvc = MockMvcBuilders.standaloneSetup(dogResource)
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
    public static Dog createEntity(EntityManager em) {
        Dog dog = new Dog()
            .puceId(DEFAULT_PUCE_ID)
            .tatooId(DEFAULT_TATOO_ID)
            .passportId(DEFAULT_PASSPORT_ID)
            .name(DEFAULT_NAME)
            .name1(DEFAULT_NAME_1)
            .birthday(DEFAULT_BIRTHDAY)
            .sexe(DEFAULT_SEXE)
            .hcNum(DEFAULT_HC_NUM);
        return dog;
    }

    @Before
    public void initTest() {
        dog = createEntity(em);
    }

    @Test
    @Transactional
    public void createDog() throws Exception {
        int databaseSizeBeforeCreate = dogRepository.findAll().size();

        // Create the Dog
        restDogMockMvc.perform(post("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dog)))
            .andExpect(status().isCreated());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeCreate + 1);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getPuceId()).isEqualTo(DEFAULT_PUCE_ID);
        assertThat(testDog.getTatooId()).isEqualTo(DEFAULT_TATOO_ID);
        assertThat(testDog.getPassportId()).isEqualTo(DEFAULT_PASSPORT_ID);
        assertThat(testDog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDog.getName1()).isEqualTo(DEFAULT_NAME_1);
        assertThat(testDog.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testDog.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testDog.getHcNum()).isEqualTo(DEFAULT_HC_NUM);
    }

    @Test
    @Transactional
    public void createDogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dogRepository.findAll().size();

        // Create the Dog with an existing ID
        dog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDogMockMvc.perform(post("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dog)))
            .andExpect(status().isBadRequest());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDogs() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        // Get all the dogList
        restDogMockMvc.perform(get("/api/dogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dog.getId().intValue())))
            .andExpect(jsonPath("$.[*].puceId").value(hasItem(DEFAULT_PUCE_ID)))
            .andExpect(jsonPath("$.[*].tatooId").value(hasItem(DEFAULT_TATOO_ID.toString())))
            .andExpect(jsonPath("$.[*].passportId").value(hasItem(DEFAULT_PASSPORT_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].name1").value(hasItem(DEFAULT_NAME_1.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].hcNum").value(hasItem(DEFAULT_HC_NUM.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDogsWithEagerRelationshipsIsEnabled() throws Exception {
        DogResource dogResource = new DogResource(dogRepositoryMock);
        when(dogRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDogMockMvc = MockMvcBuilders.standaloneSetup(dogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDogMockMvc.perform(get("/api/dogs?eagerload=true"))
        .andExpect(status().isOk());

        verify(dogRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDogsWithEagerRelationshipsIsNotEnabled() throws Exception {
        DogResource dogResource = new DogResource(dogRepositoryMock);
            when(dogRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDogMockMvc = MockMvcBuilders.standaloneSetup(dogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDogMockMvc.perform(get("/api/dogs?eagerload=true"))
        .andExpect(status().isOk());

            verify(dogRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        // Get the dog
        restDogMockMvc.perform(get("/api/dogs/{id}", dog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dog.getId().intValue()))
            .andExpect(jsonPath("$.puceId").value(DEFAULT_PUCE_ID))
            .andExpect(jsonPath("$.tatooId").value(DEFAULT_TATOO_ID.toString()))
            .andExpect(jsonPath("$.passportId").value(DEFAULT_PASSPORT_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.name1").value(DEFAULT_NAME_1.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.hcNum").value(DEFAULT_HC_NUM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDog() throws Exception {
        // Get the dog
        restDogMockMvc.perform(get("/api/dogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Update the dog
        Dog updatedDog = dogRepository.findById(dog.getId()).get();
        // Disconnect from session so that the updates on updatedDog are not directly saved in db
        em.detach(updatedDog);
        updatedDog
            .puceId(UPDATED_PUCE_ID)
            .tatooId(UPDATED_TATOO_ID)
            .passportId(UPDATED_PASSPORT_ID)
            .name(UPDATED_NAME)
            .name1(UPDATED_NAME_1)
            .birthday(UPDATED_BIRTHDAY)
            .sexe(UPDATED_SEXE)
            .hcNum(UPDATED_HC_NUM);

        restDogMockMvc.perform(put("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDog)))
            .andExpect(status().isOk());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getPuceId()).isEqualTo(UPDATED_PUCE_ID);
        assertThat(testDog.getTatooId()).isEqualTo(UPDATED_TATOO_ID);
        assertThat(testDog.getPassportId()).isEqualTo(UPDATED_PASSPORT_ID);
        assertThat(testDog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDog.getName1()).isEqualTo(UPDATED_NAME_1);
        assertThat(testDog.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testDog.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testDog.getHcNum()).isEqualTo(UPDATED_HC_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingDog() throws Exception {
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Create the Dog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDogMockMvc.perform(put("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dog)))
            .andExpect(status().isBadRequest());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        int databaseSizeBeforeDelete = dogRepository.findAll().size();

        // Delete the dog
        restDogMockMvc.perform(delete("/api/dogs/{id}", dog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dog.class);
        Dog dog1 = new Dog();
        dog1.setId(1L);
        Dog dog2 = new Dog();
        dog2.setId(dog1.getId());
        assertThat(dog1).isEqualTo(dog2);
        dog2.setId(2L);
        assertThat(dog1).isNotEqualTo(dog2);
        dog1.setId(null);
        assertThat(dog1).isNotEqualTo(dog2);
    }
}

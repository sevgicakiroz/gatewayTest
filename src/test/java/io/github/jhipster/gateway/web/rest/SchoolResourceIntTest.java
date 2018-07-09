package io.github.jhipster.gateway.web.rest;

import io.github.jhipster.gateway.GatewayTestApp;

import io.github.jhipster.gateway.domain.School;
import io.github.jhipster.gateway.repository.SchoolRepository;
import io.github.jhipster.gateway.repository.search.SchoolSearchRepository;
import io.github.jhipster.gateway.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.gateway.domain.enumeration.Status;
/**
 * Test class for the SchoolResource REST controller.
 *
 * @see SchoolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayTestApp.class)
public class SchoolResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_FOUNDED_YEAR = 1;
    private static final Integer UPDATED_FOUNDED_YEAR = 2;

    private static final String DEFAULT_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_VICE_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_VICE_PRINCIPAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_STUDENT_COUNT = 1;
    private static final Integer UPDATED_STUDENT_COUNT = 2;

    private static final Integer DEFAULT_EMPLOYEE_COUNT = 1;
    private static final Integer UPDATED_EMPLOYEE_COUNT = 2;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.PASSIVE;

    @Autowired
    private SchoolRepository schoolRepository;


    /**
     * This repository is mocked in the io.github.jhipster.gateway.repository.search test package.
     *
     * @see io.github.jhipster.gateway.repository.search.SchoolSearchRepositoryMockConfiguration
     */
    @Autowired
    private SchoolSearchRepository mockSchoolSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchoolMockMvc;

    private School school;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchoolResource schoolResource = new SchoolResource(schoolRepository, mockSchoolSearchRepository);
        this.restSchoolMockMvc = MockMvcBuilders.standaloneSetup(schoolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createEntity(EntityManager em) {
        School school = new School()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .foundedYear(DEFAULT_FOUNDED_YEAR)
            .principal(DEFAULT_PRINCIPAL)
            .vicePrincipal(DEFAULT_VICE_PRINCIPAL)
            .studentCount(DEFAULT_STUDENT_COUNT)
            .employeeCount(DEFAULT_EMPLOYEE_COUNT)
            .address(DEFAULT_ADDRESS)
            .status(DEFAULT_STATUS);
        return school;
    }

    @Before
    public void initTest() {
        school = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchool() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchool.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSchool.getFoundedYear()).isEqualTo(DEFAULT_FOUNDED_YEAR);
        assertThat(testSchool.getPrincipal()).isEqualTo(DEFAULT_PRINCIPAL);
        assertThat(testSchool.getVicePrincipal()).isEqualTo(DEFAULT_VICE_PRINCIPAL);
        assertThat(testSchool.getStudentCount()).isEqualTo(DEFAULT_STUDENT_COUNT);
        assertThat(testSchool.getEmployeeCount()).isEqualTo(DEFAULT_EMPLOYEE_COUNT);
        assertThat(testSchool.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSchool.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the School in Elasticsearch
        verify(mockSchoolSearchRepository, times(1)).save(testSchool);
    }

    @Test
    @Transactional
    public void createSchoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School with an existing ID
        school.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate);

        // Validate the School in Elasticsearch
        verify(mockSchoolSearchRepository, times(0)).save(school);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setName(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFoundedYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setFoundedYear(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrincipalIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setPrincipal(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStudentCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setStudentCount(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmployeeCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setEmployeeCount(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setStatus(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList
        restSchoolMockMvc.perform(get("/api/schools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].foundedYear").value(hasItem(DEFAULT_FOUNDED_YEAR)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.toString())))
            .andExpect(jsonPath("$.[*].vicePrincipal").value(hasItem(DEFAULT_VICE_PRINCIPAL.toString())))
            .andExpect(jsonPath("$.[*].studentCount").value(hasItem(DEFAULT_STUDENT_COUNT)))
            .andExpect(jsonPath("$.[*].employeeCount").value(hasItem(DEFAULT_EMPLOYEE_COUNT)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    

    @Test
    @Transactional
    public void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.foundedYear").value(DEFAULT_FOUNDED_YEAR))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.toString()))
            .andExpect(jsonPath("$.vicePrincipal").value(DEFAULT_VICE_PRINCIPAL.toString()))
            .andExpect(jsonPath("$.studentCount").value(DEFAULT_STUDENT_COUNT))
            .andExpect(jsonPath("$.employeeCount").value(DEFAULT_EMPLOYEE_COUNT))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school
        School updatedSchool = schoolRepository.findById(school.getId()).get();
        // Disconnect from session so that the updates on updatedSchool are not directly saved in db
        em.detach(updatedSchool);
        updatedSchool
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .foundedYear(UPDATED_FOUNDED_YEAR)
            .principal(UPDATED_PRINCIPAL)
            .vicePrincipal(UPDATED_VICE_PRINCIPAL)
            .studentCount(UPDATED_STUDENT_COUNT)
            .employeeCount(UPDATED_EMPLOYEE_COUNT)
            .address(UPDATED_ADDRESS)
            .status(UPDATED_STATUS);

        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchool)))
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchool.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSchool.getFoundedYear()).isEqualTo(UPDATED_FOUNDED_YEAR);
        assertThat(testSchool.getPrincipal()).isEqualTo(UPDATED_PRINCIPAL);
        assertThat(testSchool.getVicePrincipal()).isEqualTo(UPDATED_VICE_PRINCIPAL);
        assertThat(testSchool.getStudentCount()).isEqualTo(UPDATED_STUDENT_COUNT);
        assertThat(testSchool.getEmployeeCount()).isEqualTo(UPDATED_EMPLOYEE_COUNT);
        assertThat(testSchool.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSchool.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the School in Elasticsearch
        verify(mockSchoolSearchRepository, times(1)).save(testSchool);
    }

    @Test
    @Transactional
    public void updateNonExistingSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Create the School

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);

        // Validate the School in Elasticsearch
        verify(mockSchoolSearchRepository, times(0)).save(school);
    }

    @Test
    @Transactional
    public void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeDelete = schoolRepository.findAll().size();

        // Get the school
        restSchoolMockMvc.perform(delete("/api/schools/{id}", school.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the School in Elasticsearch
        verify(mockSchoolSearchRepository, times(1)).deleteById(school.getId());
    }

    @Test
    @Transactional
    public void searchSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        when(mockSchoolSearchRepository.search(queryStringQuery("id:" + school.getId())))
            .thenReturn(Collections.singletonList(school));
        // Search the school
        restSchoolMockMvc.perform(get("/api/_search/schools?query=id:" + school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].foundedYear").value(hasItem(DEFAULT_FOUNDED_YEAR)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.toString())))
            .andExpect(jsonPath("$.[*].vicePrincipal").value(hasItem(DEFAULT_VICE_PRINCIPAL.toString())))
            .andExpect(jsonPath("$.[*].studentCount").value(hasItem(DEFAULT_STUDENT_COUNT)))
            .andExpect(jsonPath("$.[*].employeeCount").value(hasItem(DEFAULT_EMPLOYEE_COUNT)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(School.class);
        School school1 = new School();
        school1.setId(1L);
        School school2 = new School();
        school2.setId(school1.getId());
        assertThat(school1).isEqualTo(school2);
        school2.setId(2L);
        assertThat(school1).isNotEqualTo(school2);
        school1.setId(null);
        assertThat(school1).isNotEqualTo(school2);
    }
}

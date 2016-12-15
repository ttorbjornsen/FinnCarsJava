package ttorbjornsen.finncars.web.rest;

import ttorbjornsen.finncars.AbstractCassandraTest;
import ttorbjornsen.finncars.FinnCarsApp;

import ttorbjornsen.finncars.domain.Acq_car_header;
import ttorbjornsen.finncars.repository.Acq_car_headerRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Acq_car_headerResource REST controller.
 *
 * @see Acq_car_headerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinnCarsApp.class)
public class Acq_car_headerResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LOAD_DATE = "AAAAAAAAAA";
    private static final String UPDATED_LOAD_DATE = "BBBBBBBBBB";

    private static final Long DEFAULT_LOAD_TIME = 1L;
    private static final Long UPDATED_LOAD_TIME = 2L;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_KM = "AAAAAAAAAA";
    private static final String UPDATED_KM = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE = "BBBBBBBBBB";

    @Inject
    private Acq_car_headerRepository acq_car_headerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAcq_car_headerMockMvc;

    private Acq_car_header acq_car_header;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Acq_car_headerResource acq_car_headerResource = new Acq_car_headerResource();
        ReflectionTestUtils.setField(acq_car_headerResource, "acq_car_headerRepository", acq_car_headerRepository);
        this.restAcq_car_headerMockMvc = MockMvcBuilders.standaloneSetup(acq_car_headerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acq_car_header createEntity() {
        Acq_car_header acq_car_header = new Acq_car_header()
                .url(DEFAULT_URL)
                .load_date(DEFAULT_LOAD_DATE)
                .load_time(DEFAULT_LOAD_TIME)
                .location(DEFAULT_LOCATION)
                .year(DEFAULT_YEAR)
                .km(DEFAULT_KM)
                .price(DEFAULT_PRICE);
        return acq_car_header;
    }

    @Before
    public void initTest() {
        acq_car_headerRepository.deleteAll();
        acq_car_header = createEntity();
    }

    @Test
    public void createAcq_car_header() throws Exception {
        int databaseSizeBeforeCreate = acq_car_headerRepository.findAll().size();

        // Create the Acq_car_header

        restAcq_car_headerMockMvc.perform(post("/api/acq-car-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acq_car_header)))
            .andExpect(status().isCreated());

        // Validate the Acq_car_header in the database
        List<Acq_car_header> acq_car_headerList = acq_car_headerRepository.findAll();
        assertThat(acq_car_headerList).hasSize(databaseSizeBeforeCreate + 1);
        Acq_car_header testAcq_car_header = acq_car_headerList.get(acq_car_headerList.size() - 1);
        assertThat(testAcq_car_header.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testAcq_car_header.getLoad_date()).isEqualTo(DEFAULT_LOAD_DATE);
        assertThat(testAcq_car_header.getLoad_time()).isEqualTo(DEFAULT_LOAD_TIME);
        assertThat(testAcq_car_header.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testAcq_car_header.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAcq_car_header.getKm()).isEqualTo(DEFAULT_KM);
        assertThat(testAcq_car_header.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    public void createAcq_car_headerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acq_car_headerRepository.findAll().size();

        // Create the Acq_car_header with an existing ID
        Acq_car_header existingAcq_car_header = new Acq_car_header();
        existingAcq_car_header.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcq_car_headerMockMvc.perform(post("/api/acq-car-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAcq_car_header)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Acq_car_header> acq_car_headerList = acq_car_headerRepository.findAll();
        assertThat(acq_car_headerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllAcq_car_headers() throws Exception {
        // Initialize the database
        acq_car_headerRepository.save(acq_car_header);

        // Get all the acq_car_headerList
        restAcq_car_headerMockMvc.perform(get("/api/acq-car-headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acq_car_header.getId().toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].load_date").value(hasItem(DEFAULT_LOAD_DATE.toString())))
            .andExpect(jsonPath("$.[*].load_time").value(hasItem(DEFAULT_LOAD_TIME.intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].km").value(hasItem(DEFAULT_KM.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.toString())));
    }

    @Test
    public void getAcq_car_header() throws Exception {
        // Initialize the database
        acq_car_headerRepository.save(acq_car_header);

        // Get the acq_car_header
        restAcq_car_headerMockMvc.perform(get("/api/acq-car-headers/{id}", acq_car_header.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acq_car_header.getId().toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.load_date").value(DEFAULT_LOAD_DATE.toString()))
            .andExpect(jsonPath("$.load_time").value(DEFAULT_LOAD_TIME.intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.km").value(DEFAULT_KM.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.toString()));
    }

    @Test
    public void getNonExistingAcq_car_header() throws Exception {
        // Get the acq_car_header
        restAcq_car_headerMockMvc.perform(get("/api/acq-car-headers/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAcq_car_header() throws Exception {
        // Initialize the database
        acq_car_headerRepository.save(acq_car_header);
        int databaseSizeBeforeUpdate = acq_car_headerRepository.findAll().size();

        // Update the acq_car_header
        Acq_car_header updatedAcq_car_header = acq_car_headerRepository.findOne(acq_car_header.getId());
        updatedAcq_car_header
                .url(UPDATED_URL)
                .load_date(UPDATED_LOAD_DATE)
                .load_time(UPDATED_LOAD_TIME)
                .location(UPDATED_LOCATION)
                .year(UPDATED_YEAR)
                .km(UPDATED_KM)
                .price(UPDATED_PRICE);

        restAcq_car_headerMockMvc.perform(put("/api/acq-car-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcq_car_header)))
            .andExpect(status().isOk());

        // Validate the Acq_car_header in the database
        List<Acq_car_header> acq_car_headerList = acq_car_headerRepository.findAll();
        assertThat(acq_car_headerList).hasSize(databaseSizeBeforeUpdate);
        Acq_car_header testAcq_car_header = acq_car_headerList.get(acq_car_headerList.size() - 1);
        assertThat(testAcq_car_header.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAcq_car_header.getLoad_date()).isEqualTo(UPDATED_LOAD_DATE);
        assertThat(testAcq_car_header.getLoad_time()).isEqualTo(UPDATED_LOAD_TIME);
        assertThat(testAcq_car_header.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testAcq_car_header.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAcq_car_header.getKm()).isEqualTo(UPDATED_KM);
        assertThat(testAcq_car_header.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    public void updateNonExistingAcq_car_header() throws Exception {
        int databaseSizeBeforeUpdate = acq_car_headerRepository.findAll().size();

        // Create the Acq_car_header

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAcq_car_headerMockMvc.perform(put("/api/acq-car-headers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acq_car_header)))
            .andExpect(status().isCreated());

        // Validate the Acq_car_header in the database
        List<Acq_car_header> acq_car_headerList = acq_car_headerRepository.findAll();
        assertThat(acq_car_headerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAcq_car_header() throws Exception {
        // Initialize the database
        acq_car_headerRepository.save(acq_car_header);
        int databaseSizeBeforeDelete = acq_car_headerRepository.findAll().size();

        // Get the acq_car_header
        restAcq_car_headerMockMvc.perform(delete("/api/acq-car-headers/{id}", acq_car_header.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Acq_car_header> acq_car_headerList = acq_car_headerRepository.findAll();
        assertThat(acq_car_headerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterDlApplicationApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Kpi;
import com.mycompany.myapp.domain.Factory;
import com.mycompany.myapp.repository.KpiRepository;
import com.mycompany.myapp.service.KpiService;
import com.mycompany.myapp.service.dto.KpiDTO;
import com.mycompany.myapp.service.mapper.KpiMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link KpiResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterDlApplicationApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class KpiResourceIT {

    private static final ZonedDateTime DEFAULT_RESULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESULT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private KpiRepository kpiRepository;

    @Autowired
    private KpiMapper kpiMapper;

    @Autowired
    private KpiService kpiService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKpiMockMvc;

    private Kpi kpi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpi createEntity(EntityManager em) {
        Kpi kpi = new Kpi()
            .resultTime(DEFAULT_RESULT_TIME)
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE);
        // Add required entity
        Factory factory;
        if (TestUtil.findAll(em, Factory.class).isEmpty()) {
            factory = FactoryResourceIT.createEntity(em);
            em.persist(factory);
            em.flush();
        } else {
            factory = TestUtil.findAll(em, Factory.class).get(0);
        }
        kpi.setFactory(factory);
        return kpi;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpi createUpdatedEntity(EntityManager em) {
        Kpi kpi = new Kpi()
            .resultTime(UPDATED_RESULT_TIME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);
        // Add required entity
        Factory factory;
        if (TestUtil.findAll(em, Factory.class).isEmpty()) {
            factory = FactoryResourceIT.createUpdatedEntity(em);
            em.persist(factory);
            em.flush();
        } else {
            factory = TestUtil.findAll(em, Factory.class).get(0);
        }
        kpi.setFactory(factory);
        return kpi;
    }

    @BeforeEach
    public void initTest() {
        kpi = createEntity(em);
    }

    @Test
    @Transactional
    public void createKpi() throws Exception {
        int databaseSizeBeforeCreate = kpiRepository.findAll().size();

        // Create the Kpi
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);
        restKpiMockMvc.perform(post("/api/kpis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isCreated());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeCreate + 1);
        Kpi testKpi = kpiList.get(kpiList.size() - 1);
        assertThat(testKpi.getResultTime()).isEqualTo(DEFAULT_RESULT_TIME);
        assertThat(testKpi.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testKpi.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createKpiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kpiRepository.findAll().size();

        // Create the Kpi with an existing ID
        kpi.setId(1L);
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKpiMockMvc.perform(post("/api/kpis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkResultTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setResultTime(null);

        // Create the Kpi, which fails.
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);

        restKpiMockMvc.perform(post("/api/kpis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setType(null);

        // Create the Kpi, which fails.
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);

        restKpiMockMvc.perform(post("/api/kpis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setValue(null);

        // Create the Kpi, which fails.
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);

        restKpiMockMvc.perform(post("/api/kpis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKpis() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList
        restKpiMockMvc.perform(get("/api/kpis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kpi.getId().intValue())))
            .andExpect(jsonPath("$.[*].resultTime").value(hasItem(sameInstant(DEFAULT_RESULT_TIME))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getKpi() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get the kpi
        restKpiMockMvc.perform(get("/api/kpis/{id}", kpi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kpi.getId().intValue()))
            .andExpect(jsonPath("$.resultTime").value(sameInstant(DEFAULT_RESULT_TIME)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingKpi() throws Exception {
        // Get the kpi
        restKpiMockMvc.perform(get("/api/kpis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKpi() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();

        // Update the kpi
        Kpi updatedKpi = kpiRepository.findById(kpi.getId()).get();
        // Disconnect from session so that the updates on updatedKpi are not directly saved in db
        em.detach(updatedKpi);
        updatedKpi
            .resultTime(UPDATED_RESULT_TIME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);
        KpiDTO kpiDTO = kpiMapper.toDto(updatedKpi);

        restKpiMockMvc.perform(put("/api/kpis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isOk());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
        Kpi testKpi = kpiList.get(kpiList.size() - 1);
        assertThat(testKpi.getResultTime()).isEqualTo(UPDATED_RESULT_TIME);
        assertThat(testKpi.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testKpi.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingKpi() throws Exception {
        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();

        // Create the Kpi
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpiMockMvc.perform(put("/api/kpis").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKpi() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        int databaseSizeBeforeDelete = kpiRepository.findAll().size();

        // Delete the kpi
        restKpiMockMvc.perform(delete("/api/kpis/{id}", kpi.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

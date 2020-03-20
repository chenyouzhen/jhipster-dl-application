package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterDlApplicationApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Logistics;
import com.mycompany.myapp.repository.LogisticsRepository;
import com.mycompany.myapp.service.LogisticsService;
import com.mycompany.myapp.service.dto.LogisticsDTO;
import com.mycompany.myapp.service.mapper.LogisticsMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LogisticsResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterDlApplicationApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class LogisticsResourceIT {

    private static final String DEFAULT_LOGISTICS_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOGISTICS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EXPRESS_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_EXPRESS_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_EXPRESS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EXPRESS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_START_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_START_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_END_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_END_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_POSITION = "BBBBBBBBBB";

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLogisticsMockMvc;

    private Logistics logistics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Logistics createEntity(EntityManager em) {
        Logistics logistics = new Logistics()
            .logisticsId(DEFAULT_LOGISTICS_ID)
            .expressCompany(DEFAULT_EXPRESS_COMPANY)
            .expressNumber(DEFAULT_EXPRESS_NUMBER)
            .status(DEFAULT_STATUS)
            .startPosition(DEFAULT_START_POSITION)
            .endPosition(DEFAULT_END_POSITION)
            .currentPosition(DEFAULT_CURRENT_POSITION);
        return logistics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Logistics createUpdatedEntity(EntityManager em) {
        Logistics logistics = new Logistics()
            .logisticsId(UPDATED_LOGISTICS_ID)
            .expressCompany(UPDATED_EXPRESS_COMPANY)
            .expressNumber(UPDATED_EXPRESS_NUMBER)
            .status(UPDATED_STATUS)
            .startPosition(UPDATED_START_POSITION)
            .endPosition(UPDATED_END_POSITION)
            .currentPosition(UPDATED_CURRENT_POSITION);
        return logistics;
    }

    @BeforeEach
    public void initTest() {
        logistics = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogistics() throws Exception {
        int databaseSizeBeforeCreate = logisticsRepository.findAll().size();

        // Create the Logistics
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);
        restLogisticsMockMvc.perform(post("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isCreated());

        // Validate the Logistics in the database
        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeCreate + 1);
        Logistics testLogistics = logisticsList.get(logisticsList.size() - 1);
        assertThat(testLogistics.getLogisticsId()).isEqualTo(DEFAULT_LOGISTICS_ID);
        assertThat(testLogistics.getExpressCompany()).isEqualTo(DEFAULT_EXPRESS_COMPANY);
        assertThat(testLogistics.getExpressNumber()).isEqualTo(DEFAULT_EXPRESS_NUMBER);
        assertThat(testLogistics.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLogistics.getStartPosition()).isEqualTo(DEFAULT_START_POSITION);
        assertThat(testLogistics.getEndPosition()).isEqualTo(DEFAULT_END_POSITION);
        assertThat(testLogistics.getCurrentPosition()).isEqualTo(DEFAULT_CURRENT_POSITION);
    }

    @Test
    @Transactional
    public void createLogisticsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logisticsRepository.findAll().size();

        // Create the Logistics with an existing ID
        logistics.setId(1L);
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogisticsMockMvc.perform(post("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Logistics in the database
        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLogisticsIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = logisticsRepository.findAll().size();
        // set the field null
        logistics.setLogisticsId(null);

        // Create the Logistics, which fails.
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);

        restLogisticsMockMvc.perform(post("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isBadRequest());

        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = logisticsRepository.findAll().size();
        // set the field null
        logistics.setStatus(null);

        // Create the Logistics, which fails.
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);

        restLogisticsMockMvc.perform(post("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isBadRequest());

        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogistics() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get all the logisticsList
        restLogisticsMockMvc.perform(get("/api/logistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].logisticsId").value(hasItem(DEFAULT_LOGISTICS_ID)))
            .andExpect(jsonPath("$.[*].expressCompany").value(hasItem(DEFAULT_EXPRESS_COMPANY)))
            .andExpect(jsonPath("$.[*].expressNumber").value(hasItem(DEFAULT_EXPRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].startPosition").value(hasItem(DEFAULT_START_POSITION)))
            .andExpect(jsonPath("$.[*].endPosition").value(hasItem(DEFAULT_END_POSITION)))
            .andExpect(jsonPath("$.[*].currentPosition").value(hasItem(DEFAULT_CURRENT_POSITION)));
    }
    
    @Test
    @Transactional
    public void getLogistics() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        // Get the logistics
        restLogisticsMockMvc.perform(get("/api/logistics/{id}", logistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(logistics.getId().intValue()))
            .andExpect(jsonPath("$.logisticsId").value(DEFAULT_LOGISTICS_ID))
            .andExpect(jsonPath("$.expressCompany").value(DEFAULT_EXPRESS_COMPANY))
            .andExpect(jsonPath("$.expressNumber").value(DEFAULT_EXPRESS_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.startPosition").value(DEFAULT_START_POSITION))
            .andExpect(jsonPath("$.endPosition").value(DEFAULT_END_POSITION))
            .andExpect(jsonPath("$.currentPosition").value(DEFAULT_CURRENT_POSITION));
    }

    @Test
    @Transactional
    public void getNonExistingLogistics() throws Exception {
        // Get the logistics
        restLogisticsMockMvc.perform(get("/api/logistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogistics() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        int databaseSizeBeforeUpdate = logisticsRepository.findAll().size();

        // Update the logistics
        Logistics updatedLogistics = logisticsRepository.findById(logistics.getId()).get();
        // Disconnect from session so that the updates on updatedLogistics are not directly saved in db
        em.detach(updatedLogistics);
        updatedLogistics
            .logisticsId(UPDATED_LOGISTICS_ID)
            .expressCompany(UPDATED_EXPRESS_COMPANY)
            .expressNumber(UPDATED_EXPRESS_NUMBER)
            .status(UPDATED_STATUS)
            .startPosition(UPDATED_START_POSITION)
            .endPosition(UPDATED_END_POSITION)
            .currentPosition(UPDATED_CURRENT_POSITION);
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(updatedLogistics);

        restLogisticsMockMvc.perform(put("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isOk());

        // Validate the Logistics in the database
        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeUpdate);
        Logistics testLogistics = logisticsList.get(logisticsList.size() - 1);
        assertThat(testLogistics.getLogisticsId()).isEqualTo(UPDATED_LOGISTICS_ID);
        assertThat(testLogistics.getExpressCompany()).isEqualTo(UPDATED_EXPRESS_COMPANY);
        assertThat(testLogistics.getExpressNumber()).isEqualTo(UPDATED_EXPRESS_NUMBER);
        assertThat(testLogistics.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLogistics.getStartPosition()).isEqualTo(UPDATED_START_POSITION);
        assertThat(testLogistics.getEndPosition()).isEqualTo(UPDATED_END_POSITION);
        assertThat(testLogistics.getCurrentPosition()).isEqualTo(UPDATED_CURRENT_POSITION);
    }

    @Test
    @Transactional
    public void updateNonExistingLogistics() throws Exception {
        int databaseSizeBeforeUpdate = logisticsRepository.findAll().size();

        // Create the Logistics
        LogisticsDTO logisticsDTO = logisticsMapper.toDto(logistics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogisticsMockMvc.perform(put("/api/logistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(logisticsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Logistics in the database
        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogistics() throws Exception {
        // Initialize the database
        logisticsRepository.saveAndFlush(logistics);

        int databaseSizeBeforeDelete = logisticsRepository.findAll().size();

        // Delete the logistics
        restLogisticsMockMvc.perform(delete("/api/logistics/{id}", logistics.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Logistics> logisticsList = logisticsRepository.findAll();
        assertThat(logisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

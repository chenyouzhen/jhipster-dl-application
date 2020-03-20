package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterDlApplicationApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Demand;
import com.mycompany.myapp.repository.DemandRepository;
import com.mycompany.myapp.service.DemandService;
import com.mycompany.myapp.service.dto.DemandDTO;
import com.mycompany.myapp.service.mapper.DemandMapper;

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
 * Integration tests for the {@link DemandResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterDlApplicationApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class DemandResourceIT {

    private static final String DEFAULT_DEMAND_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEMAND_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private DemandMapper demandMapper;

    @Autowired
    private DemandService demandService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandMockMvc;

    private Demand demand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demand createEntity(EntityManager em) {
        Demand demand = new Demand()
            .demandId(DEFAULT_DEMAND_ID)
            .quantity(DEFAULT_QUANTITY);
        return demand;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demand createUpdatedEntity(EntityManager em) {
        Demand demand = new Demand()
            .demandId(UPDATED_DEMAND_ID)
            .quantity(UPDATED_QUANTITY);
        return demand;
    }

    @BeforeEach
    public void initTest() {
        demand = createEntity(em);
    }

    @Test
    @Transactional
    public void createDemand() throws Exception {
        int databaseSizeBeforeCreate = demandRepository.findAll().size();

        // Create the Demand
        DemandDTO demandDTO = demandMapper.toDto(demand);
        restDemandMockMvc.perform(post("/api/demands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandDTO)))
            .andExpect(status().isCreated());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeCreate + 1);
        Demand testDemand = demandList.get(demandList.size() - 1);
        assertThat(testDemand.getDemandId()).isEqualTo(DEFAULT_DEMAND_ID);
        assertThat(testDemand.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createDemandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = demandRepository.findAll().size();

        // Create the Demand with an existing ID
        demand.setId(1L);
        DemandDTO demandDTO = demandMapper.toDto(demand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandMockMvc.perform(post("/api/demands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDemandIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandRepository.findAll().size();
        // set the field null
        demand.setDemandId(null);

        // Create the Demand, which fails.
        DemandDTO demandDTO = demandMapper.toDto(demand);

        restDemandMockMvc.perform(post("/api/demands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandDTO)))
            .andExpect(status().isBadRequest());

        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandRepository.findAll().size();
        // set the field null
        demand.setQuantity(null);

        // Create the Demand, which fails.
        DemandDTO demandDTO = demandMapper.toDto(demand);

        restDemandMockMvc.perform(post("/api/demands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandDTO)))
            .andExpect(status().isBadRequest());

        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDemands() throws Exception {
        // Initialize the database
        demandRepository.saveAndFlush(demand);

        // Get all the demandList
        restDemandMockMvc.perform(get("/api/demands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demand.getId().intValue())))
            .andExpect(jsonPath("$.[*].demandId").value(hasItem(DEFAULT_DEMAND_ID)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())));
    }
    
    @Test
    @Transactional
    public void getDemand() throws Exception {
        // Initialize the database
        demandRepository.saveAndFlush(demand);

        // Get the demand
        restDemandMockMvc.perform(get("/api/demands/{id}", demand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demand.getId().intValue()))
            .andExpect(jsonPath("$.demandId").value(DEFAULT_DEMAND_ID))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDemand() throws Exception {
        // Get the demand
        restDemandMockMvc.perform(get("/api/demands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDemand() throws Exception {
        // Initialize the database
        demandRepository.saveAndFlush(demand);

        int databaseSizeBeforeUpdate = demandRepository.findAll().size();

        // Update the demand
        Demand updatedDemand = demandRepository.findById(demand.getId()).get();
        // Disconnect from session so that the updates on updatedDemand are not directly saved in db
        em.detach(updatedDemand);
        updatedDemand
            .demandId(UPDATED_DEMAND_ID)
            .quantity(UPDATED_QUANTITY);
        DemandDTO demandDTO = demandMapper.toDto(updatedDemand);

        restDemandMockMvc.perform(put("/api/demands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandDTO)))
            .andExpect(status().isOk());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
        Demand testDemand = demandList.get(demandList.size() - 1);
        assertThat(testDemand.getDemandId()).isEqualTo(UPDATED_DEMAND_ID);
        assertThat(testDemand.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingDemand() throws Exception {
        int databaseSizeBeforeUpdate = demandRepository.findAll().size();

        // Create the Demand
        DemandDTO demandDTO = demandMapper.toDto(demand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandMockMvc.perform(put("/api/demands").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(demandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDemand() throws Exception {
        // Initialize the database
        demandRepository.saveAndFlush(demand);

        int databaseSizeBeforeDelete = demandRepository.findAll().size();

        // Delete the demand
        restDemandMockMvc.perform(delete("/api/demands/{id}", demand.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

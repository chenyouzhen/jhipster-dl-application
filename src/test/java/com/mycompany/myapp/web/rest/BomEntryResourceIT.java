package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterDlApplicationApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.BomEntry;
import com.mycompany.myapp.repository.BomEntryRepository;
import com.mycompany.myapp.service.BomEntryService;
import com.mycompany.myapp.service.dto.BomEntryDTO;
import com.mycompany.myapp.service.mapper.BomEntryMapper;

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
 * Integration tests for the {@link BomEntryResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterDlApplicationApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class BomEntryResourceIT {

    private static final Long DEFAULT_MATERIAL = 1L;
    private static final Long UPDATED_MATERIAL = 2L;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    @Autowired
    private BomEntryRepository bomEntryRepository;

    @Autowired
    private BomEntryMapper bomEntryMapper;

    @Autowired
    private BomEntryService bomEntryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBomEntryMockMvc;

    private BomEntry bomEntry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BomEntry createEntity(EntityManager em) {
        BomEntry bomEntry = new BomEntry()
            .material(DEFAULT_MATERIAL)
            .amount(DEFAULT_AMOUNT);
        return bomEntry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BomEntry createUpdatedEntity(EntityManager em) {
        BomEntry bomEntry = new BomEntry()
            .material(UPDATED_MATERIAL)
            .amount(UPDATED_AMOUNT);
        return bomEntry;
    }

    @BeforeEach
    public void initTest() {
        bomEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createBomEntry() throws Exception {
        int databaseSizeBeforeCreate = bomEntryRepository.findAll().size();

        // Create the BomEntry
        BomEntryDTO bomEntryDTO = bomEntryMapper.toDto(bomEntry);
        restBomEntryMockMvc.perform(post("/api/bom-entries").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bomEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the BomEntry in the database
        List<BomEntry> bomEntryList = bomEntryRepository.findAll();
        assertThat(bomEntryList).hasSize(databaseSizeBeforeCreate + 1);
        BomEntry testBomEntry = bomEntryList.get(bomEntryList.size() - 1);
        assertThat(testBomEntry.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testBomEntry.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createBomEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bomEntryRepository.findAll().size();

        // Create the BomEntry with an existing ID
        bomEntry.setId(1L);
        BomEntryDTO bomEntryDTO = bomEntryMapper.toDto(bomEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBomEntryMockMvc.perform(post("/api/bom-entries").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bomEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BomEntry in the database
        List<BomEntry> bomEntryList = bomEntryRepository.findAll();
        assertThat(bomEntryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMaterialIsRequired() throws Exception {
        int databaseSizeBeforeTest = bomEntryRepository.findAll().size();
        // set the field null
        bomEntry.setMaterial(null);

        // Create the BomEntry, which fails.
        BomEntryDTO bomEntryDTO = bomEntryMapper.toDto(bomEntry);

        restBomEntryMockMvc.perform(post("/api/bom-entries").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bomEntryDTO)))
            .andExpect(status().isBadRequest());

        List<BomEntry> bomEntryList = bomEntryRepository.findAll();
        assertThat(bomEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = bomEntryRepository.findAll().size();
        // set the field null
        bomEntry.setAmount(null);

        // Create the BomEntry, which fails.
        BomEntryDTO bomEntryDTO = bomEntryMapper.toDto(bomEntry);

        restBomEntryMockMvc.perform(post("/api/bom-entries").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bomEntryDTO)))
            .andExpect(status().isBadRequest());

        List<BomEntry> bomEntryList = bomEntryRepository.findAll();
        assertThat(bomEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBomEntries() throws Exception {
        // Initialize the database
        bomEntryRepository.saveAndFlush(bomEntry);

        // Get all the bomEntryList
        restBomEntryMockMvc.perform(get("/api/bom-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bomEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getBomEntry() throws Exception {
        // Initialize the database
        bomEntryRepository.saveAndFlush(bomEntry);

        // Get the bomEntry
        restBomEntryMockMvc.perform(get("/api/bom-entries/{id}", bomEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bomEntry.getId().intValue()))
            .andExpect(jsonPath("$.material").value(DEFAULT_MATERIAL.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBomEntry() throws Exception {
        // Get the bomEntry
        restBomEntryMockMvc.perform(get("/api/bom-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBomEntry() throws Exception {
        // Initialize the database
        bomEntryRepository.saveAndFlush(bomEntry);

        int databaseSizeBeforeUpdate = bomEntryRepository.findAll().size();

        // Update the bomEntry
        BomEntry updatedBomEntry = bomEntryRepository.findById(bomEntry.getId()).get();
        // Disconnect from session so that the updates on updatedBomEntry are not directly saved in db
        em.detach(updatedBomEntry);
        updatedBomEntry
            .material(UPDATED_MATERIAL)
            .amount(UPDATED_AMOUNT);
        BomEntryDTO bomEntryDTO = bomEntryMapper.toDto(updatedBomEntry);

        restBomEntryMockMvc.perform(put("/api/bom-entries").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bomEntryDTO)))
            .andExpect(status().isOk());

        // Validate the BomEntry in the database
        List<BomEntry> bomEntryList = bomEntryRepository.findAll();
        assertThat(bomEntryList).hasSize(databaseSizeBeforeUpdate);
        BomEntry testBomEntry = bomEntryList.get(bomEntryList.size() - 1);
        assertThat(testBomEntry.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testBomEntry.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingBomEntry() throws Exception {
        int databaseSizeBeforeUpdate = bomEntryRepository.findAll().size();

        // Create the BomEntry
        BomEntryDTO bomEntryDTO = bomEntryMapper.toDto(bomEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBomEntryMockMvc.perform(put("/api/bom-entries").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bomEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BomEntry in the database
        List<BomEntry> bomEntryList = bomEntryRepository.findAll();
        assertThat(bomEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBomEntry() throws Exception {
        // Initialize the database
        bomEntryRepository.saveAndFlush(bomEntry);

        int databaseSizeBeforeDelete = bomEntryRepository.findAll().size();

        // Delete the bomEntry
        restBomEntryMockMvc.perform(delete("/api/bom-entries/{id}", bomEntry.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BomEntry> bomEntryList = bomEntryRepository.findAll();
        assertThat(bomEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

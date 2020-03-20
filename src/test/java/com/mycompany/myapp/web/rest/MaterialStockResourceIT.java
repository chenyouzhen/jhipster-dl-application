package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterDlApplicationApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.MaterialStock;
import com.mycompany.myapp.repository.MaterialStockRepository;
import com.mycompany.myapp.service.MaterialStockService;
import com.mycompany.myapp.service.dto.MaterialStockDTO;
import com.mycompany.myapp.service.mapper.MaterialStockMapper;

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
 * Integration tests for the {@link MaterialStockResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterDlApplicationApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class MaterialStockResourceIT {

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    @Autowired
    private MaterialStockRepository materialStockRepository;

    @Autowired
    private MaterialStockMapper materialStockMapper;

    @Autowired
    private MaterialStockService materialStockService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialStockMockMvc;

    private MaterialStock materialStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialStock createEntity(EntityManager em) {
        MaterialStock materialStock = new MaterialStock()
            .quantity(DEFAULT_QUANTITY)
            .unit(DEFAULT_UNIT);
        return materialStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialStock createUpdatedEntity(EntityManager em) {
        MaterialStock materialStock = new MaterialStock()
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT);
        return materialStock;
    }

    @BeforeEach
    public void initTest() {
        materialStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterialStock() throws Exception {
        int databaseSizeBeforeCreate = materialStockRepository.findAll().size();

        // Create the MaterialStock
        MaterialStockDTO materialStockDTO = materialStockMapper.toDto(materialStock);
        restMaterialStockMockMvc.perform(post("/api/material-stocks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialStockDTO)))
            .andExpect(status().isCreated());

        // Validate the MaterialStock in the database
        List<MaterialStock> materialStockList = materialStockRepository.findAll();
        assertThat(materialStockList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialStock testMaterialStock = materialStockList.get(materialStockList.size() - 1);
        assertThat(testMaterialStock.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMaterialStock.getUnit()).isEqualTo(DEFAULT_UNIT);
    }

    @Test
    @Transactional
    public void createMaterialStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialStockRepository.findAll().size();

        // Create the MaterialStock with an existing ID
        materialStock.setId(1L);
        MaterialStockDTO materialStockDTO = materialStockMapper.toDto(materialStock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialStockMockMvc.perform(post("/api/material-stocks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialStock in the database
        List<MaterialStock> materialStockList = materialStockRepository.findAll();
        assertThat(materialStockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialStockRepository.findAll().size();
        // set the field null
        materialStock.setQuantity(null);

        // Create the MaterialStock, which fails.
        MaterialStockDTO materialStockDTO = materialStockMapper.toDto(materialStock);

        restMaterialStockMockMvc.perform(post("/api/material-stocks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialStockDTO)))
            .andExpect(status().isBadRequest());

        List<MaterialStock> materialStockList = materialStockRepository.findAll();
        assertThat(materialStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterialStocks() throws Exception {
        // Initialize the database
        materialStockRepository.saveAndFlush(materialStock);

        // Get all the materialStockList
        restMaterialStockMockMvc.perform(get("/api/material-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)));
    }
    
    @Test
    @Transactional
    public void getMaterialStock() throws Exception {
        // Initialize the database
        materialStockRepository.saveAndFlush(materialStock);

        // Get the materialStock
        restMaterialStockMockMvc.perform(get("/api/material-stocks/{id}", materialStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialStock.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT));
    }

    @Test
    @Transactional
    public void getNonExistingMaterialStock() throws Exception {
        // Get the materialStock
        restMaterialStockMockMvc.perform(get("/api/material-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterialStock() throws Exception {
        // Initialize the database
        materialStockRepository.saveAndFlush(materialStock);

        int databaseSizeBeforeUpdate = materialStockRepository.findAll().size();

        // Update the materialStock
        MaterialStock updatedMaterialStock = materialStockRepository.findById(materialStock.getId()).get();
        // Disconnect from session so that the updates on updatedMaterialStock are not directly saved in db
        em.detach(updatedMaterialStock);
        updatedMaterialStock
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT);
        MaterialStockDTO materialStockDTO = materialStockMapper.toDto(updatedMaterialStock);

        restMaterialStockMockMvc.perform(put("/api/material-stocks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialStockDTO)))
            .andExpect(status().isOk());

        // Validate the MaterialStock in the database
        List<MaterialStock> materialStockList = materialStockRepository.findAll();
        assertThat(materialStockList).hasSize(databaseSizeBeforeUpdate);
        MaterialStock testMaterialStock = materialStockList.get(materialStockList.size() - 1);
        assertThat(testMaterialStock.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMaterialStock.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterialStock() throws Exception {
        int databaseSizeBeforeUpdate = materialStockRepository.findAll().size();

        // Create the MaterialStock
        MaterialStockDTO materialStockDTO = materialStockMapper.toDto(materialStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialStockMockMvc.perform(put("/api/material-stocks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materialStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MaterialStock in the database
        List<MaterialStock> materialStockList = materialStockRepository.findAll();
        assertThat(materialStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaterialStock() throws Exception {
        // Initialize the database
        materialStockRepository.saveAndFlush(materialStock);

        int databaseSizeBeforeDelete = materialStockRepository.findAll().size();

        // Delete the materialStock
        restMaterialStockMockMvc.perform(delete("/api/material-stocks/{id}", materialStock.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialStock> materialStockList = materialStockRepository.findAll();
        assertThat(materialStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

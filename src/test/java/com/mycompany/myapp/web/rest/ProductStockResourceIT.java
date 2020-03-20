package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterDlApplicationApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.ProductStock;
import com.mycompany.myapp.repository.ProductStockRepository;
import com.mycompany.myapp.service.ProductStockService;
import com.mycompany.myapp.service.dto.ProductStockDTO;
import com.mycompany.myapp.service.mapper.ProductStockMapper;

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
 * Integration tests for the {@link ProductStockResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterDlApplicationApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class ProductStockResourceIT {

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    @Autowired
    private ProductStockRepository productStockRepository;

    @Autowired
    private ProductStockMapper productStockMapper;

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductStockMockMvc;

    private ProductStock productStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStock createEntity(EntityManager em) {
        ProductStock productStock = new ProductStock()
            .quantity(DEFAULT_QUANTITY)
            .unit(DEFAULT_UNIT);
        return productStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStock createUpdatedEntity(EntityManager em) {
        ProductStock productStock = new ProductStock()
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT);
        return productStock;
    }

    @BeforeEach
    public void initTest() {
        productStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductStock() throws Exception {
        int databaseSizeBeforeCreate = productStockRepository.findAll().size();

        // Create the ProductStock
        ProductStockDTO productStockDTO = productStockMapper.toDto(productStock);
        restProductStockMockMvc.perform(post("/api/product-stocks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStockDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductStock in the database
        List<ProductStock> productStockList = productStockRepository.findAll();
        assertThat(productStockList).hasSize(databaseSizeBeforeCreate + 1);
        ProductStock testProductStock = productStockList.get(productStockList.size() - 1);
        assertThat(testProductStock.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProductStock.getUnit()).isEqualTo(DEFAULT_UNIT);
    }

    @Test
    @Transactional
    public void createProductStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productStockRepository.findAll().size();

        // Create the ProductStock with an existing ID
        productStock.setId(1L);
        ProductStockDTO productStockDTO = productStockMapper.toDto(productStock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductStockMockMvc.perform(post("/api/product-stocks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStock in the database
        List<ProductStock> productStockList = productStockRepository.findAll();
        assertThat(productStockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = productStockRepository.findAll().size();
        // set the field null
        productStock.setQuantity(null);

        // Create the ProductStock, which fails.
        ProductStockDTO productStockDTO = productStockMapper.toDto(productStock);

        restProductStockMockMvc.perform(post("/api/product-stocks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStockDTO)))
            .andExpect(status().isBadRequest());

        List<ProductStock> productStockList = productStockRepository.findAll();
        assertThat(productStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductStocks() throws Exception {
        // Initialize the database
        productStockRepository.saveAndFlush(productStock);

        // Get all the productStockList
        restProductStockMockMvc.perform(get("/api/product-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)));
    }
    
    @Test
    @Transactional
    public void getProductStock() throws Exception {
        // Initialize the database
        productStockRepository.saveAndFlush(productStock);

        // Get the productStock
        restProductStockMockMvc.perform(get("/api/product-stocks/{id}", productStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productStock.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT));
    }

    @Test
    @Transactional
    public void getNonExistingProductStock() throws Exception {
        // Get the productStock
        restProductStockMockMvc.perform(get("/api/product-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductStock() throws Exception {
        // Initialize the database
        productStockRepository.saveAndFlush(productStock);

        int databaseSizeBeforeUpdate = productStockRepository.findAll().size();

        // Update the productStock
        ProductStock updatedProductStock = productStockRepository.findById(productStock.getId()).get();
        // Disconnect from session so that the updates on updatedProductStock are not directly saved in db
        em.detach(updatedProductStock);
        updatedProductStock
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT);
        ProductStockDTO productStockDTO = productStockMapper.toDto(updatedProductStock);

        restProductStockMockMvc.perform(put("/api/product-stocks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStockDTO)))
            .andExpect(status().isOk());

        // Validate the ProductStock in the database
        List<ProductStock> productStockList = productStockRepository.findAll();
        assertThat(productStockList).hasSize(databaseSizeBeforeUpdate);
        ProductStock testProductStock = productStockList.get(productStockList.size() - 1);
        assertThat(testProductStock.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProductStock.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void updateNonExistingProductStock() throws Exception {
        int databaseSizeBeforeUpdate = productStockRepository.findAll().size();

        // Create the ProductStock
        ProductStockDTO productStockDTO = productStockMapper.toDto(productStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductStockMockMvc.perform(put("/api/product-stocks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStock in the database
        List<ProductStock> productStockList = productStockRepository.findAll();
        assertThat(productStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductStock() throws Exception {
        // Initialize the database
        productStockRepository.saveAndFlush(productStock);

        int databaseSizeBeforeDelete = productStockRepository.findAll().size();

        // Delete the productStock
        restProductStockMockMvc.perform(delete("/api/product-stocks/{id}", productStock.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductStock> productStockList = productStockRepository.findAll();
        assertThat(productStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterDlApplicationApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.AssemblyLine;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.repository.AssemblyLineRepository;
import com.mycompany.myapp.service.AssemblyLineService;
import com.mycompany.myapp.service.dto.AssemblyLineDTO;
import com.mycompany.myapp.service.mapper.AssemblyLineMapper;

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
 * Integration tests for the {@link AssemblyLineResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterDlApplicationApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class AssemblyLineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CAPACITY = 1L;
    private static final Long UPDATED_CAPACITY = 2L;

    private static final Long DEFAULT_PLAN_CAPACITY = 1L;
    private static final Long UPDATED_PLAN_CAPACITY = 2L;

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    @Autowired
    private AssemblyLineRepository assemblyLineRepository;

    @Autowired
    private AssemblyLineMapper assemblyLineMapper;

    @Autowired
    private AssemblyLineService assemblyLineService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssemblyLineMockMvc;

    private AssemblyLine assemblyLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssemblyLine createEntity(EntityManager em) {
        AssemblyLine assemblyLine = new AssemblyLine()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .capacity(DEFAULT_CAPACITY)
            .planCapacity(DEFAULT_PLAN_CAPACITY)
            .deviceId(DEFAULT_DEVICE_ID);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        assemblyLine.setProduct(product);
        return assemblyLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssemblyLine createUpdatedEntity(EntityManager em) {
        AssemblyLine assemblyLine = new AssemblyLine()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .capacity(UPDATED_CAPACITY)
            .planCapacity(UPDATED_PLAN_CAPACITY)
            .deviceId(UPDATED_DEVICE_ID);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        assemblyLine.setProduct(product);
        return assemblyLine;
    }

    @BeforeEach
    public void initTest() {
        assemblyLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssemblyLine() throws Exception {
        int databaseSizeBeforeCreate = assemblyLineRepository.findAll().size();

        // Create the AssemblyLine
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);
        restAssemblyLineMockMvc.perform(post("/api/assembly-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isCreated());

        // Validate the AssemblyLine in the database
        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeCreate + 1);
        AssemblyLine testAssemblyLine = assemblyLineList.get(assemblyLineList.size() - 1);
        assertThat(testAssemblyLine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssemblyLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssemblyLine.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testAssemblyLine.getPlanCapacity()).isEqualTo(DEFAULT_PLAN_CAPACITY);
        assertThat(testAssemblyLine.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
    }

    @Test
    @Transactional
    public void createAssemblyLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assemblyLineRepository.findAll().size();

        // Create the AssemblyLine with an existing ID
        assemblyLine.setId(1L);
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssemblyLineMockMvc.perform(post("/api/assembly-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssemblyLine in the database
        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assemblyLineRepository.findAll().size();
        // set the field null
        assemblyLine.setName(null);

        // Create the AssemblyLine, which fails.
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        restAssemblyLineMockMvc.perform(post("/api/assembly-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = assemblyLineRepository.findAll().size();
        // set the field null
        assemblyLine.setCapacity(null);

        // Create the AssemblyLine, which fails.
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        restAssemblyLineMockMvc.perform(post("/api/assembly-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlanCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = assemblyLineRepository.findAll().size();
        // set the field null
        assemblyLine.setPlanCapacity(null);

        // Create the AssemblyLine, which fails.
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        restAssemblyLineMockMvc.perform(post("/api/assembly-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = assemblyLineRepository.findAll().size();
        // set the field null
        assemblyLine.setDeviceId(null);

        // Create the AssemblyLine, which fails.
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        restAssemblyLineMockMvc.perform(post("/api/assembly-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssemblyLines() throws Exception {
        // Initialize the database
        assemblyLineRepository.saveAndFlush(assemblyLine);

        // Get all the assemblyLineList
        restAssemblyLineMockMvc.perform(get("/api/assembly-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assemblyLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY.intValue())))
            .andExpect(jsonPath("$.[*].planCapacity").value(hasItem(DEFAULT_PLAN_CAPACITY.intValue())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID)));
    }
    
    @Test
    @Transactional
    public void getAssemblyLine() throws Exception {
        // Initialize the database
        assemblyLineRepository.saveAndFlush(assemblyLine);

        // Get the assemblyLine
        restAssemblyLineMockMvc.perform(get("/api/assembly-lines/{id}", assemblyLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assemblyLine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY.intValue()))
            .andExpect(jsonPath("$.planCapacity").value(DEFAULT_PLAN_CAPACITY.intValue()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingAssemblyLine() throws Exception {
        // Get the assemblyLine
        restAssemblyLineMockMvc.perform(get("/api/assembly-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssemblyLine() throws Exception {
        // Initialize the database
        assemblyLineRepository.saveAndFlush(assemblyLine);

        int databaseSizeBeforeUpdate = assemblyLineRepository.findAll().size();

        // Update the assemblyLine
        AssemblyLine updatedAssemblyLine = assemblyLineRepository.findById(assemblyLine.getId()).get();
        // Disconnect from session so that the updates on updatedAssemblyLine are not directly saved in db
        em.detach(updatedAssemblyLine);
        updatedAssemblyLine
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .capacity(UPDATED_CAPACITY)
            .planCapacity(UPDATED_PLAN_CAPACITY)
            .deviceId(UPDATED_DEVICE_ID);
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(updatedAssemblyLine);

        restAssemblyLineMockMvc.perform(put("/api/assembly-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isOk());

        // Validate the AssemblyLine in the database
        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeUpdate);
        AssemblyLine testAssemblyLine = assemblyLineList.get(assemblyLineList.size() - 1);
        assertThat(testAssemblyLine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssemblyLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssemblyLine.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testAssemblyLine.getPlanCapacity()).isEqualTo(UPDATED_PLAN_CAPACITY);
        assertThat(testAssemblyLine.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAssemblyLine() throws Exception {
        int databaseSizeBeforeUpdate = assemblyLineRepository.findAll().size();

        // Create the AssemblyLine
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssemblyLineMockMvc.perform(put("/api/assembly-lines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssemblyLine in the database
        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssemblyLine() throws Exception {
        // Initialize the database
        assemblyLineRepository.saveAndFlush(assemblyLine);

        int databaseSizeBeforeDelete = assemblyLineRepository.findAll().size();

        // Delete the assemblyLine
        restAssemblyLineMockMvc.perform(delete("/api/assembly-lines/{id}", assemblyLine.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

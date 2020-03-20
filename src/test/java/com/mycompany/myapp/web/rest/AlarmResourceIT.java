package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterDlApplicationApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Alarm;
import com.mycompany.myapp.repository.AlarmRepository;
import com.mycompany.myapp.service.AlarmService;
import com.mycompany.myapp.service.dto.AlarmDTO;
import com.mycompany.myapp.service.mapper.AlarmMapper;

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
 * Integration tests for the {@link AlarmResource} REST controller.
 */
@SpringBootTest(classes = { JhipsterDlApplicationApp.class, TestSecurityConfiguration.class })

@AutoConfigureMockMvc
@WithMockUser
public class AlarmResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RESULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESULT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RESOLVE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESOLVE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SOURCE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private AlarmMapper alarmMapper;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlarmMockMvc;

    private Alarm alarm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alarm createEntity(EntityManager em) {
        Alarm alarm = new Alarm()
            .name(DEFAULT_NAME)
            .result(DEFAULT_RESULT)
            .cause(DEFAULT_CAUSE)
            .resultTime(DEFAULT_RESULT_TIME)
            .level(DEFAULT_LEVEL)
            .status(DEFAULT_STATUS)
            .resolveTime(DEFAULT_RESOLVE_TIME)
            .sourceType(DEFAULT_SOURCE_TYPE)
            .sourceId(DEFAULT_SOURCE_ID)
            .details(DEFAULT_DETAILS);
        return alarm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alarm createUpdatedEntity(EntityManager em) {
        Alarm alarm = new Alarm()
            .name(UPDATED_NAME)
            .result(UPDATED_RESULT)
            .cause(UPDATED_CAUSE)
            .resultTime(UPDATED_RESULT_TIME)
            .level(UPDATED_LEVEL)
            .status(UPDATED_STATUS)
            .resolveTime(UPDATED_RESOLVE_TIME)
            .sourceType(UPDATED_SOURCE_TYPE)
            .sourceId(UPDATED_SOURCE_ID)
            .details(UPDATED_DETAILS);
        return alarm;
    }

    @BeforeEach
    public void initTest() {
        alarm = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarm() throws Exception {
        int databaseSizeBeforeCreate = alarmRepository.findAll().size();

        // Create the Alarm
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);
        restAlarmMockMvc.perform(post("/api/alarms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isCreated());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeCreate + 1);
        Alarm testAlarm = alarmList.get(alarmList.size() - 1);
        assertThat(testAlarm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlarm.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testAlarm.getCause()).isEqualTo(DEFAULT_CAUSE);
        assertThat(testAlarm.getResultTime()).isEqualTo(DEFAULT_RESULT_TIME);
        assertThat(testAlarm.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testAlarm.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAlarm.getResolveTime()).isEqualTo(DEFAULT_RESOLVE_TIME);
        assertThat(testAlarm.getSourceType()).isEqualTo(DEFAULT_SOURCE_TYPE);
        assertThat(testAlarm.getSourceId()).isEqualTo(DEFAULT_SOURCE_ID);
        assertThat(testAlarm.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    public void createAlarmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmRepository.findAll().size();

        // Create the Alarm with an existing ID
        alarm.setId(1L);
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmMockMvc.perform(post("/api/alarms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRepository.findAll().size();
        // set the field null
        alarm.setResult(null);

        // Create the Alarm, which fails.
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        restAlarmMockMvc.perform(post("/api/alarms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRepository.findAll().size();
        // set the field null
        alarm.setResultTime(null);

        // Create the Alarm, which fails.
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        restAlarmMockMvc.perform(post("/api/alarms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRepository.findAll().size();
        // set the field null
        alarm.setStatus(null);

        // Create the Alarm, which fails.
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        restAlarmMockMvc.perform(post("/api/alarms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSourceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRepository.findAll().size();
        // set the field null
        alarm.setSourceType(null);

        // Create the Alarm, which fails.
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        restAlarmMockMvc.perform(post("/api/alarms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSourceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = alarmRepository.findAll().size();
        // set the field null
        alarm.setSourceId(null);

        // Create the Alarm, which fails.
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        restAlarmMockMvc.perform(post("/api/alarms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlarms() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        // Get all the alarmList
        restAlarmMockMvc.perform(get("/api/alarms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarm.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE)))
            .andExpect(jsonPath("$.[*].resultTime").value(hasItem(sameInstant(DEFAULT_RESULT_TIME))))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].resolveTime").value(hasItem(sameInstant(DEFAULT_RESOLVE_TIME))))
            .andExpect(jsonPath("$.[*].sourceType").value(hasItem(DEFAULT_SOURCE_TYPE)))
            .andExpect(jsonPath("$.[*].sourceId").value(hasItem(DEFAULT_SOURCE_ID)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }
    
    @Test
    @Transactional
    public void getAlarm() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        // Get the alarm
        restAlarmMockMvc.perform(get("/api/alarms/{id}", alarm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alarm.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE))
            .andExpect(jsonPath("$.resultTime").value(sameInstant(DEFAULT_RESULT_TIME)))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.resolveTime").value(sameInstant(DEFAULT_RESOLVE_TIME)))
            .andExpect(jsonPath("$.sourceType").value(DEFAULT_SOURCE_TYPE))
            .andExpect(jsonPath("$.sourceId").value(DEFAULT_SOURCE_ID))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }

    @Test
    @Transactional
    public void getNonExistingAlarm() throws Exception {
        // Get the alarm
        restAlarmMockMvc.perform(get("/api/alarms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarm() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        int databaseSizeBeforeUpdate = alarmRepository.findAll().size();

        // Update the alarm
        Alarm updatedAlarm = alarmRepository.findById(alarm.getId()).get();
        // Disconnect from session so that the updates on updatedAlarm are not directly saved in db
        em.detach(updatedAlarm);
        updatedAlarm
            .name(UPDATED_NAME)
            .result(UPDATED_RESULT)
            .cause(UPDATED_CAUSE)
            .resultTime(UPDATED_RESULT_TIME)
            .level(UPDATED_LEVEL)
            .status(UPDATED_STATUS)
            .resolveTime(UPDATED_RESOLVE_TIME)
            .sourceType(UPDATED_SOURCE_TYPE)
            .sourceId(UPDATED_SOURCE_ID)
            .details(UPDATED_DETAILS);
        AlarmDTO alarmDTO = alarmMapper.toDto(updatedAlarm);

        restAlarmMockMvc.perform(put("/api/alarms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isOk());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeUpdate);
        Alarm testAlarm = alarmList.get(alarmList.size() - 1);
        assertThat(testAlarm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlarm.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testAlarm.getCause()).isEqualTo(UPDATED_CAUSE);
        assertThat(testAlarm.getResultTime()).isEqualTo(UPDATED_RESULT_TIME);
        assertThat(testAlarm.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testAlarm.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAlarm.getResolveTime()).isEqualTo(UPDATED_RESOLVE_TIME);
        assertThat(testAlarm.getSourceType()).isEqualTo(UPDATED_SOURCE_TYPE);
        assertThat(testAlarm.getSourceId()).isEqualTo(UPDATED_SOURCE_ID);
        assertThat(testAlarm.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarm() throws Exception {
        int databaseSizeBeforeUpdate = alarmRepository.findAll().size();

        // Create the Alarm
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmMockMvc.perform(put("/api/alarms").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlarm() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        int databaseSizeBeforeDelete = alarmRepository.findAll().size();

        // Delete the alarm
        restAlarmMockMvc.perform(delete("/api/alarms/{id}", alarm.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

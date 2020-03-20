package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.AlarmService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.AlarmDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Alarm}.
 */
@RestController
@RequestMapping("/api")
public class AlarmResource {

    private final Logger log = LoggerFactory.getLogger(AlarmResource.class);

    private static final String ENTITY_NAME = "alarm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlarmService alarmService;

    public AlarmResource(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    /**
     * {@code POST  /alarms} : Create a new alarm.
     *
     * @param alarmDTO the alarmDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alarmDTO, or with status {@code 400 (Bad Request)} if the alarm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alarms")
    public ResponseEntity<AlarmDTO> createAlarm(@Valid @RequestBody AlarmDTO alarmDTO) throws URISyntaxException {
        log.debug("REST request to save Alarm : {}", alarmDTO);
        if (alarmDTO.getId() != null) {
            throw new BadRequestAlertException("A new alarm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlarmDTO result = alarmService.save(alarmDTO);
        return ResponseEntity.created(new URI("/api/alarms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alarms} : Updates an existing alarm.
     *
     * @param alarmDTO the alarmDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alarmDTO,
     * or with status {@code 400 (Bad Request)} if the alarmDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alarmDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alarms")
    public ResponseEntity<AlarmDTO> updateAlarm(@Valid @RequestBody AlarmDTO alarmDTO) throws URISyntaxException {
        log.debug("REST request to update Alarm : {}", alarmDTO);
        if (alarmDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlarmDTO result = alarmService.save(alarmDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alarmDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alarms} : get all the alarms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alarms in body.
     */
    @GetMapping("/alarms")
    public List<AlarmDTO> getAllAlarms() {
        log.debug("REST request to get all Alarms");
        return alarmService.findAll();
    }

    /**
     * {@code GET  /alarms/:id} : get the "id" alarm.
     *
     * @param id the id of the alarmDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarmDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alarms/{id}")
    public ResponseEntity<AlarmDTO> getAlarm(@PathVariable Long id) {
        log.debug("REST request to get Alarm : {}", id);
        Optional<AlarmDTO> alarmDTO = alarmService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alarmDTO);
    }

    /**
     * {@code DELETE  /alarms/:id} : delete the "id" alarm.
     *
     * @param id the id of the alarmDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alarms/{id}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable Long id) {
        log.debug("REST request to delete Alarm : {}", id);
        alarmService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

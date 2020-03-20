package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.RecordService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.RecordDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Record}.
 */
@RestController
@RequestMapping("/api")
public class RecordResource {

    private final Logger log = LoggerFactory.getLogger(RecordResource.class);

    private static final String ENTITY_NAME = "record";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecordService recordService;

    public RecordResource(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * {@code POST  /records} : Create a new record.
     *
     * @param recordDTO the recordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recordDTO, or with status {@code 400 (Bad Request)} if the record has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/records")
    public ResponseEntity<RecordDTO> createRecord(@Valid @RequestBody RecordDTO recordDTO) throws URISyntaxException {
        log.debug("REST request to save Record : {}", recordDTO);
        if (recordDTO.getId() != null) {
            throw new BadRequestAlertException("A new record cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecordDTO result = recordService.save(recordDTO);
        return ResponseEntity.created(new URI("/api/records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /records} : Updates an existing record.
     *
     * @param recordDTO the recordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recordDTO,
     * or with status {@code 400 (Bad Request)} if the recordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/records")
    public ResponseEntity<RecordDTO> updateRecord(@Valid @RequestBody RecordDTO recordDTO) throws URISyntaxException {
        log.debug("REST request to update Record : {}", recordDTO);
        if (recordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecordDTO result = recordService.save(recordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recordDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /records} : get all the records.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of records in body.
     */
    @GetMapping("/records")
    public List<RecordDTO> getAllRecords() {
        log.debug("REST request to get all Records");
        return recordService.findAll();
    }

    /**
     * {@code GET  /records/:id} : get the "id" record.
     *
     * @param id the id of the recordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/records/{id}")
    public ResponseEntity<RecordDTO> getRecord(@PathVariable Long id) {
        log.debug("REST request to get Record : {}", id);
        Optional<RecordDTO> recordDTO = recordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recordDTO);
    }

    /**
     * {@code DELETE  /records/:id} : delete the "id" record.
     *
     * @param id the id of the recordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/records/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        log.debug("REST request to delete Record : {}", id);
        recordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

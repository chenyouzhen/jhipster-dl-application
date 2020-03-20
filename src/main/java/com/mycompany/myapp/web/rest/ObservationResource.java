package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ObservationService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ObservationDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Observation}.
 */
@RestController
@RequestMapping("/api")
public class ObservationResource {

    private final Logger log = LoggerFactory.getLogger(ObservationResource.class);

    private static final String ENTITY_NAME = "observation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObservationService observationService;

    public ObservationResource(ObservationService observationService) {
        this.observationService = observationService;
    }

    /**
     * {@code POST  /observations} : Create a new observation.
     *
     * @param observationDTO the observationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new observationDTO, or with status {@code 400 (Bad Request)} if the observation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/observations")
    public ResponseEntity<ObservationDTO> createObservation(@Valid @RequestBody ObservationDTO observationDTO) throws URISyntaxException {
        log.debug("REST request to save Observation : {}", observationDTO);
        if (observationDTO.getId() != null) {
            throw new BadRequestAlertException("A new observation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObservationDTO result = observationService.save(observationDTO);
        return ResponseEntity.created(new URI("/api/observations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /observations} : Updates an existing observation.
     *
     * @param observationDTO the observationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observationDTO,
     * or with status {@code 400 (Bad Request)} if the observationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the observationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/observations")
    public ResponseEntity<ObservationDTO> updateObservation(@Valid @RequestBody ObservationDTO observationDTO) throws URISyntaxException {
        log.debug("REST request to update Observation : {}", observationDTO);
        if (observationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObservationDTO result = observationService.save(observationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, observationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /observations} : get all the observations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of observations in body.
     */
    @GetMapping("/observations")
    public List<ObservationDTO> getAllObservations() {
        log.debug("REST request to get all Observations");
        return observationService.findAll();
    }

    /**
     * {@code GET  /observations/:id} : get the "id" observation.
     *
     * @param id the id of the observationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the observationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/observations/{id}")
    public ResponseEntity<ObservationDTO> getObservation(@PathVariable Long id) {
        log.debug("REST request to get Observation : {}", id);
        Optional<ObservationDTO> observationDTO = observationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(observationDTO);
    }

    /**
     * {@code DELETE  /observations/:id} : delete the "id" observation.
     *
     * @param id the id of the observationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/observations/{id}")
    public ResponseEntity<Void> deleteObservation(@PathVariable Long id) {
        log.debug("REST request to delete Observation : {}", id);
        observationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

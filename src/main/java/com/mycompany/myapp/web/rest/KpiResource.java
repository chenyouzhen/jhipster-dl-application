package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.KpiService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.KpiDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Kpi}.
 */
@RestController
@RequestMapping("/api")
public class KpiResource {

    private final Logger log = LoggerFactory.getLogger(KpiResource.class);

    private static final String ENTITY_NAME = "kpi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KpiService kpiService;

    public KpiResource(KpiService kpiService) {
        this.kpiService = kpiService;
    }

    /**
     * {@code POST  /kpis} : Create a new kpi.
     *
     * @param kpiDTO the kpiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kpiDTO, or with status {@code 400 (Bad Request)} if the kpi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kpis")
    public ResponseEntity<KpiDTO> createKpi(@Valid @RequestBody KpiDTO kpiDTO) throws URISyntaxException {
        log.debug("REST request to save Kpi : {}", kpiDTO);
        if (kpiDTO.getId() != null) {
            throw new BadRequestAlertException("A new kpi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KpiDTO result = kpiService.save(kpiDTO);
        return ResponseEntity.created(new URI("/api/kpis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kpis} : Updates an existing kpi.
     *
     * @param kpiDTO the kpiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpiDTO,
     * or with status {@code 400 (Bad Request)} if the kpiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kpiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kpis")
    public ResponseEntity<KpiDTO> updateKpi(@Valid @RequestBody KpiDTO kpiDTO) throws URISyntaxException {
        log.debug("REST request to update Kpi : {}", kpiDTO);
        if (kpiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KpiDTO result = kpiService.save(kpiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kpis} : get all the kpis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kpis in body.
     */
    @GetMapping("/kpis")
    public List<KpiDTO> getAllKpis() {
        log.debug("REST request to get all Kpis");
        return kpiService.findAll();
    }

    /**
     * {@code GET  /kpis/:id} : get the "id" kpi.
     *
     * @param id the id of the kpiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kpiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kpis/{id}")
    public ResponseEntity<KpiDTO> getKpi(@PathVariable Long id) {
        log.debug("REST request to get Kpi : {}", id);
        Optional<KpiDTO> kpiDTO = kpiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kpiDTO);
    }

    /**
     * {@code DELETE  /kpis/:id} : delete the "id" kpi.
     *
     * @param id the id of the kpiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kpis/{id}")
    public ResponseEntity<Void> deleteKpi(@PathVariable Long id) {
        log.debug("REST request to delete Kpi : {}", id);
        kpiService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.BomEntryService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.BomEntryDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.BomEntry}.
 */
@RestController
@RequestMapping("/api")
public class BomEntryResource {

    private final Logger log = LoggerFactory.getLogger(BomEntryResource.class);

    private static final String ENTITY_NAME = "bomEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BomEntryService bomEntryService;

    public BomEntryResource(BomEntryService bomEntryService) {
        this.bomEntryService = bomEntryService;
    }

    /**
     * {@code POST  /bom-entries} : Create a new bomEntry.
     *
     * @param bomEntryDTO the bomEntryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bomEntryDTO, or with status {@code 400 (Bad Request)} if the bomEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bom-entries")
    public ResponseEntity<BomEntryDTO> createBomEntry(@Valid @RequestBody BomEntryDTO bomEntryDTO) throws URISyntaxException {
        log.debug("REST request to save BomEntry : {}", bomEntryDTO);
        if (bomEntryDTO.getId() != null) {
            throw new BadRequestAlertException("A new bomEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BomEntryDTO result = bomEntryService.save(bomEntryDTO);
        return ResponseEntity.created(new URI("/api/bom-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bom-entries} : Updates an existing bomEntry.
     *
     * @param bomEntryDTO the bomEntryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bomEntryDTO,
     * or with status {@code 400 (Bad Request)} if the bomEntryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bomEntryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bom-entries")
    public ResponseEntity<BomEntryDTO> updateBomEntry(@Valid @RequestBody BomEntryDTO bomEntryDTO) throws URISyntaxException {
        log.debug("REST request to update BomEntry : {}", bomEntryDTO);
        if (bomEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BomEntryDTO result = bomEntryService.save(bomEntryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bomEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bom-entries} : get all the bomEntries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bomEntries in body.
     */
    @GetMapping("/bom-entries")
    public List<BomEntryDTO> getAllBomEntries() {
        log.debug("REST request to get all BomEntries");
        return bomEntryService.findAll();
    }

    /**
     * {@code GET  /bom-entries/:id} : get the "id" bomEntry.
     *
     * @param id the id of the bomEntryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bomEntryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bom-entries/{id}")
    public ResponseEntity<BomEntryDTO> getBomEntry(@PathVariable Long id) {
        log.debug("REST request to get BomEntry : {}", id);
        Optional<BomEntryDTO> bomEntryDTO = bomEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bomEntryDTO);
    }

    /**
     * {@code DELETE  /bom-entries/:id} : delete the "id" bomEntry.
     *
     * @param id the id of the bomEntryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bom-entries/{id}")
    public ResponseEntity<Void> deleteBomEntry(@PathVariable Long id) {
        log.debug("REST request to delete BomEntry : {}", id);
        bomEntryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

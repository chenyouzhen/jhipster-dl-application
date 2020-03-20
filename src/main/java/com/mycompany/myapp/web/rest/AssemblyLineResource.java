package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.AssemblyLineService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.AssemblyLineDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.AssemblyLine}.
 */
@RestController
@RequestMapping("/api")
public class AssemblyLineResource {

    private final Logger log = LoggerFactory.getLogger(AssemblyLineResource.class);

    private static final String ENTITY_NAME = "assemblyLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssemblyLineService assemblyLineService;

    public AssemblyLineResource(AssemblyLineService assemblyLineService) {
        this.assemblyLineService = assemblyLineService;
    }

    /**
     * {@code POST  /assembly-lines} : Create a new assemblyLine.
     *
     * @param assemblyLineDTO the assemblyLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assemblyLineDTO, or with status {@code 400 (Bad Request)} if the assemblyLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assembly-lines")
    public ResponseEntity<AssemblyLineDTO> createAssemblyLine(@Valid @RequestBody AssemblyLineDTO assemblyLineDTO) throws URISyntaxException {
        log.debug("REST request to save AssemblyLine : {}", assemblyLineDTO);
        if (assemblyLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new assemblyLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssemblyLineDTO result = assemblyLineService.save(assemblyLineDTO);
        return ResponseEntity.created(new URI("/api/assembly-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /assembly-lines} : Updates an existing assemblyLine.
     *
     * @param assemblyLineDTO the assemblyLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assemblyLineDTO,
     * or with status {@code 400 (Bad Request)} if the assemblyLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assemblyLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assembly-lines")
    public ResponseEntity<AssemblyLineDTO> updateAssemblyLine(@Valid @RequestBody AssemblyLineDTO assemblyLineDTO) throws URISyntaxException {
        log.debug("REST request to update AssemblyLine : {}", assemblyLineDTO);
        if (assemblyLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssemblyLineDTO result = assemblyLineService.save(assemblyLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assemblyLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /assembly-lines} : get all the assemblyLines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assemblyLines in body.
     */
    @GetMapping("/assembly-lines")
    public List<AssemblyLineDTO> getAllAssemblyLines() {
        log.debug("REST request to get all AssemblyLines");
        return assemblyLineService.findAll();
    }

    /**
     * {@code GET  /assembly-lines/:id} : get the "id" assemblyLine.
     *
     * @param id the id of the assemblyLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assemblyLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assembly-lines/{id}")
    public ResponseEntity<AssemblyLineDTO> getAssemblyLine(@PathVariable Long id) {
        log.debug("REST request to get AssemblyLine : {}", id);
        Optional<AssemblyLineDTO> assemblyLineDTO = assemblyLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assemblyLineDTO);
    }

    /**
     * {@code DELETE  /assembly-lines/:id} : delete the "id" assemblyLine.
     *
     * @param id the id of the assemblyLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assembly-lines/{id}")
    public ResponseEntity<Void> deleteAssemblyLine(@PathVariable Long id) {
        log.debug("REST request to delete AssemblyLine : {}", id);
        assemblyLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.FactoryService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.FactoryDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Factory}.
 */
@RestController
@RequestMapping("/api")
public class FactoryResource {

    private final Logger log = LoggerFactory.getLogger(FactoryResource.class);

    private static final String ENTITY_NAME = "factory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FactoryService factoryService;

    public FactoryResource(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    /**
     * {@code POST  /factories} : Create a new factory.
     *
     * @param factoryDTO the factoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new factoryDTO, or with status {@code 400 (Bad Request)} if the factory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/factories")
    public ResponseEntity<FactoryDTO> createFactory(@Valid @RequestBody FactoryDTO factoryDTO) throws URISyntaxException {
        log.debug("REST request to save Factory : {}", factoryDTO);
        if (factoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new factory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactoryDTO result = factoryService.save(factoryDTO);
        return ResponseEntity.created(new URI("/api/factories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /factories} : Updates an existing factory.
     *
     * @param factoryDTO the factoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated factoryDTO,
     * or with status {@code 400 (Bad Request)} if the factoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the factoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/factories")
    public ResponseEntity<FactoryDTO> updateFactory(@Valid @RequestBody FactoryDTO factoryDTO) throws URISyntaxException {
        log.debug("REST request to update Factory : {}", factoryDTO);
        if (factoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FactoryDTO result = factoryService.save(factoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, factoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /factories} : get all the factories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of factories in body.
     */
    @GetMapping("/factories")
    public ResponseEntity<List<FactoryDTO>> getAllFactories(Pageable pageable) {
        log.debug("REST request to get a page of Factories");
        Page<FactoryDTO> page = factoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /factories/:id} : get the "id" factory.
     *
     * @param id the id of the factoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the factoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/factories/{id}")
    public ResponseEntity<FactoryDTO> getFactory(@PathVariable Long id) {
        log.debug("REST request to get Factory : {}", id);
        Optional<FactoryDTO> factoryDTO = factoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factoryDTO);
    }

    /**
     * {@code DELETE  /factories/:id} : delete the "id" factory.
     *
     * @param id the id of the factoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/factories/{id}")
    public ResponseEntity<Void> deleteFactory(@PathVariable Long id) {
        log.debug("REST request to delete Factory : {}", id);
        factoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

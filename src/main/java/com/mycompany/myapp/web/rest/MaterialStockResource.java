package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.MaterialStockService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.MaterialStockDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.MaterialStock}.
 */
@RestController
@RequestMapping("/api")
public class MaterialStockResource {

    private final Logger log = LoggerFactory.getLogger(MaterialStockResource.class);

    private static final String ENTITY_NAME = "materialStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialStockService materialStockService;

    public MaterialStockResource(MaterialStockService materialStockService) {
        this.materialStockService = materialStockService;
    }

    /**
     * {@code POST  /material-stocks} : Create a new materialStock.
     *
     * @param materialStockDTO the materialStockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialStockDTO, or with status {@code 400 (Bad Request)} if the materialStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/material-stocks")
    public ResponseEntity<MaterialStockDTO> createMaterialStock(@Valid @RequestBody MaterialStockDTO materialStockDTO) throws URISyntaxException {
        log.debug("REST request to save MaterialStock : {}", materialStockDTO);
        if (materialStockDTO.getId() != null) {
            throw new BadRequestAlertException("A new materialStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialStockDTO result = materialStockService.save(materialStockDTO);
        return ResponseEntity.created(new URI("/api/material-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /material-stocks} : Updates an existing materialStock.
     *
     * @param materialStockDTO the materialStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialStockDTO,
     * or with status {@code 400 (Bad Request)} if the materialStockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/material-stocks")
    public ResponseEntity<MaterialStockDTO> updateMaterialStock(@Valid @RequestBody MaterialStockDTO materialStockDTO) throws URISyntaxException {
        log.debug("REST request to update MaterialStock : {}", materialStockDTO);
        if (materialStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaterialStockDTO result = materialStockService.save(materialStockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, materialStockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /material-stocks} : get all the materialStocks.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialStocks in body.
     */
    @GetMapping("/material-stocks")
    public List<MaterialStockDTO> getAllMaterialStocks(@RequestParam(required = false) String filter) {
        if ("materialid-is-null".equals(filter)) {
            log.debug("REST request to get all MaterialStocks where materialId is null");
            return materialStockService.findAllWhereMaterialIdIsNull();
        }
        log.debug("REST request to get all MaterialStocks");
        return materialStockService.findAll();
    }

    /**
     * {@code GET  /material-stocks/:id} : get the "id" materialStock.
     *
     * @param id the id of the materialStockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialStockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/material-stocks/{id}")
    public ResponseEntity<MaterialStockDTO> getMaterialStock(@PathVariable Long id) {
        log.debug("REST request to get MaterialStock : {}", id);
        Optional<MaterialStockDTO> materialStockDTO = materialStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialStockDTO);
    }

    /**
     * {@code DELETE  /material-stocks/:id} : delete the "id" materialStock.
     *
     * @param id the id of the materialStockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/material-stocks/{id}")
    public ResponseEntity<Void> deleteMaterialStock(@PathVariable Long id) {
        log.debug("REST request to delete MaterialStock : {}", id);
        materialStockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

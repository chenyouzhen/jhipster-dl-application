package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ProductStockService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.ProductStockDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.ProductStock}.
 */
@RestController
@RequestMapping("/api")
public class ProductStockResource {

    private final Logger log = LoggerFactory.getLogger(ProductStockResource.class);

    private static final String ENTITY_NAME = "productStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductStockService productStockService;

    public ProductStockResource(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    /**
     * {@code POST  /product-stocks} : Create a new productStock.
     *
     * @param productStockDTO the productStockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productStockDTO, or with status {@code 400 (Bad Request)} if the productStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-stocks")
    public ResponseEntity<ProductStockDTO> createProductStock(@Valid @RequestBody ProductStockDTO productStockDTO) throws URISyntaxException {
        log.debug("REST request to save ProductStock : {}", productStockDTO);
        if (productStockDTO.getId() != null) {
            throw new BadRequestAlertException("A new productStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductStockDTO result = productStockService.save(productStockDTO);
        return ResponseEntity.created(new URI("/api/product-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-stocks} : Updates an existing productStock.
     *
     * @param productStockDTO the productStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productStockDTO,
     * or with status {@code 400 (Bad Request)} if the productStockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-stocks")
    public ResponseEntity<ProductStockDTO> updateProductStock(@Valid @RequestBody ProductStockDTO productStockDTO) throws URISyntaxException {
        log.debug("REST request to update ProductStock : {}", productStockDTO);
        if (productStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductStockDTO result = productStockService.save(productStockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productStockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-stocks} : get all the productStocks.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productStocks in body.
     */
    @GetMapping("/product-stocks")
    public List<ProductStockDTO> getAllProductStocks(@RequestParam(required = false) String filter) {
        if ("productid-is-null".equals(filter)) {
            log.debug("REST request to get all ProductStocks where productId is null");
            return productStockService.findAllWhereProductIdIsNull();
        }
        log.debug("REST request to get all ProductStocks");
        return productStockService.findAll();
    }

    /**
     * {@code GET  /product-stocks/:id} : get the "id" productStock.
     *
     * @param id the id of the productStockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productStockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-stocks/{id}")
    public ResponseEntity<ProductStockDTO> getProductStock(@PathVariable Long id) {
        log.debug("REST request to get ProductStock : {}", id);
        Optional<ProductStockDTO> productStockDTO = productStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productStockDTO);
    }

    /**
     * {@code DELETE  /product-stocks/:id} : delete the "id" productStock.
     *
     * @param id the id of the productStockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-stocks/{id}")
    public ResponseEntity<Void> deleteProductStock(@PathVariable Long id) {
        log.debug("REST request to delete ProductStock : {}", id);
        productStockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

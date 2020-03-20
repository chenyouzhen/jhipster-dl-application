package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ProductStockDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ProductStock}.
 */
public interface ProductStockService {

    /**
     * Save a productStock.
     *
     * @param productStockDTO the entity to save.
     * @return the persisted entity.
     */
    ProductStockDTO save(ProductStockDTO productStockDTO);

    /**
     * Get all the productStocks.
     *
     * @return the list of entities.
     */
    List<ProductStockDTO> findAll();
    /**
     * Get all the ProductStockDTO where ProductId is {@code null}.
     *
     * @return the list of entities.
     */
    List<ProductStockDTO> findAllWhereProductIdIsNull();

    /**
     * Get the "id" productStock.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductStockDTO> findOne(Long id);

    /**
     * Delete the "id" productStock.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ProductStockService;
import com.mycompany.myapp.domain.ProductStock;
import com.mycompany.myapp.repository.ProductStockRepository;
import com.mycompany.myapp.service.dto.ProductStockDTO;
import com.mycompany.myapp.service.mapper.ProductStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link ProductStock}.
 */
@Service
@Transactional
public class ProductStockServiceImpl implements ProductStockService {

    private final Logger log = LoggerFactory.getLogger(ProductStockServiceImpl.class);

    private final ProductStockRepository productStockRepository;

    private final ProductStockMapper productStockMapper;

    public ProductStockServiceImpl(ProductStockRepository productStockRepository, ProductStockMapper productStockMapper) {
        this.productStockRepository = productStockRepository;
        this.productStockMapper = productStockMapper;
    }

    /**
     * Save a productStock.
     *
     * @param productStockDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductStockDTO save(ProductStockDTO productStockDTO) {
        log.debug("Request to save ProductStock : {}", productStockDTO);
        ProductStock productStock = productStockMapper.toEntity(productStockDTO);
        productStock = productStockRepository.save(productStock);
        return productStockMapper.toDto(productStock);
    }

    /**
     * Get all the productStocks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductStockDTO> findAll() {
        log.debug("Request to get all ProductStocks");
        return productStockRepository.findAll().stream()
            .map(productStockMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the productStocks where ProductId is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ProductStockDTO> findAllWhereProductIdIsNull() {
        log.debug("Request to get all productStocks where ProductId is null");
        return StreamSupport
            .stream(productStockRepository.findAll().spliterator(), false)
            .filter(productStock -> productStock.getProductId() == null)
            .map(productStockMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one productStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductStockDTO> findOne(Long id) {
        log.debug("Request to get ProductStock : {}", id);
        return productStockRepository.findById(id)
            .map(productStockMapper::toDto);
    }

    /**
     * Delete the productStock by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductStock : {}", id);
        productStockRepository.deleteById(id);
    }
}

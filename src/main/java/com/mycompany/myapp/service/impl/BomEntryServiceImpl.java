package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BomEntryService;
import com.mycompany.myapp.domain.BomEntry;
import com.mycompany.myapp.repository.BomEntryRepository;
import com.mycompany.myapp.service.dto.BomEntryDTO;
import com.mycompany.myapp.service.mapper.BomEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BomEntry}.
 */
@Service
@Transactional
public class BomEntryServiceImpl implements BomEntryService {

    private final Logger log = LoggerFactory.getLogger(BomEntryServiceImpl.class);

    private final BomEntryRepository bomEntryRepository;

    private final BomEntryMapper bomEntryMapper;

    public BomEntryServiceImpl(BomEntryRepository bomEntryRepository, BomEntryMapper bomEntryMapper) {
        this.bomEntryRepository = bomEntryRepository;
        this.bomEntryMapper = bomEntryMapper;
    }

    /**
     * Save a bomEntry.
     *
     * @param bomEntryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BomEntryDTO save(BomEntryDTO bomEntryDTO) {
        log.debug("Request to save BomEntry : {}", bomEntryDTO);
        BomEntry bomEntry = bomEntryMapper.toEntity(bomEntryDTO);
        bomEntry = bomEntryRepository.save(bomEntry);
        return bomEntryMapper.toDto(bomEntry);
    }

    /**
     * Get all the bomEntries.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BomEntryDTO> findAll() {
        log.debug("Request to get all BomEntries");
        return bomEntryRepository.findAll().stream()
            .map(bomEntryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bomEntry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BomEntryDTO> findOne(Long id) {
        log.debug("Request to get BomEntry : {}", id);
        return bomEntryRepository.findById(id)
            .map(bomEntryMapper::toDto);
    }

    /**
     * Delete the bomEntry by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BomEntry : {}", id);
        bomEntryRepository.deleteById(id);
    }
}

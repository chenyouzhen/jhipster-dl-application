package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.KpiService;
import com.mycompany.myapp.domain.Kpi;
import com.mycompany.myapp.repository.KpiRepository;
import com.mycompany.myapp.service.dto.KpiDTO;
import com.mycompany.myapp.service.mapper.KpiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Kpi}.
 */
@Service
@Transactional
public class KpiServiceImpl implements KpiService {

    private final Logger log = LoggerFactory.getLogger(KpiServiceImpl.class);

    private final KpiRepository kpiRepository;

    private final KpiMapper kpiMapper;

    public KpiServiceImpl(KpiRepository kpiRepository, KpiMapper kpiMapper) {
        this.kpiRepository = kpiRepository;
        this.kpiMapper = kpiMapper;
    }

    /**
     * Save a kpi.
     *
     * @param kpiDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public KpiDTO save(KpiDTO kpiDTO) {
        log.debug("Request to save Kpi : {}", kpiDTO);
        Kpi kpi = kpiMapper.toEntity(kpiDTO);
        kpi = kpiRepository.save(kpi);
        return kpiMapper.toDto(kpi);
    }

    /**
     * Get all the kpis.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<KpiDTO> findAll() {
        log.debug("Request to get all Kpis");
        return kpiRepository.findAll().stream()
            .map(kpiMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one kpi by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KpiDTO> findOne(Long id) {
        log.debug("Request to get Kpi : {}", id);
        return kpiRepository.findById(id)
            .map(kpiMapper::toDto);
    }

    /**
     * Delete the kpi by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kpi : {}", id);
        kpiRepository.deleteById(id);
    }
}

package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.RecordService;
import com.mycompany.myapp.domain.Record;
import com.mycompany.myapp.repository.RecordRepository;
import com.mycompany.myapp.service.dto.RecordDTO;
import com.mycompany.myapp.service.mapper.RecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Record}.
 */
@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    private final Logger log = LoggerFactory.getLogger(RecordServiceImpl.class);

    private final RecordRepository recordRepository;

    private final RecordMapper recordMapper;

    public RecordServiceImpl(RecordRepository recordRepository, RecordMapper recordMapper) {
        this.recordRepository = recordRepository;
        this.recordMapper = recordMapper;
    }

    /**
     * Save a record.
     *
     * @param recordDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RecordDTO save(RecordDTO recordDTO) {
        log.debug("Request to save Record : {}", recordDTO);
        Record record = recordMapper.toEntity(recordDTO);
        record = recordRepository.save(record);
        return recordMapper.toDto(record);
    }

    /**
     * Get all the records.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RecordDTO> findAll() {
        log.debug("Request to get all Records");
        return recordRepository.findAll().stream()
            .map(recordMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one record by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RecordDTO> findOne(Long id) {
        log.debug("Request to get Record : {}", id);
        return recordRepository.findById(id)
            .map(recordMapper::toDto);
    }

    /**
     * Delete the record by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Record : {}", id);
        recordRepository.deleteById(id);
    }
}

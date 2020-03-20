package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.RecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Record} and its DTO {@link RecordDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface RecordMapper extends EntityMapper<RecordDTO, Record> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    RecordDTO toDto(Record record);

    @Mapping(source = "productId", target = "product")
    Record toEntity(RecordDTO recordDTO);

    default Record fromId(Long id) {
        if (id == null) {
            return null;
        }
        Record record = new Record();
        record.setId(id);
        return record;
    }
}

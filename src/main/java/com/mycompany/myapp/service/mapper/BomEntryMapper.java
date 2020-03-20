package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BomEntryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BomEntry} and its DTO {@link BomEntryDTO}.
 */
@Mapper(componentModel = "spring", uses = {MaterialMapper.class, ProductMapper.class})
public interface BomEntryMapper extends EntityMapper<BomEntryDTO, BomEntry> {

    @Mapping(source = "id.id", target = "idId")
    @Mapping(source = "productId.id", target = "productIdId")
    BomEntryDTO toDto(BomEntry bomEntry);

    @Mapping(source = "idId", target = "id")
    @Mapping(source = "productIdId", target = "productId")
    BomEntry toEntity(BomEntryDTO bomEntryDTO);

    default BomEntry fromId(Long id) {
        if (id == null) {
            return null;
        }
        BomEntry bomEntry = new BomEntry();
        bomEntry.setId(id);
        return bomEntry;
    }
}

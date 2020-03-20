package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MaterialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Material} and its DTO {@link MaterialDTO}.
 */
@Mapper(componentModel = "spring", uses = {MaterialStockMapper.class})
public interface MaterialMapper extends EntityMapper<MaterialDTO, Material> {

    @Mapping(source = "id.id", target = "idId")
    MaterialDTO toDto(Material material);

    @Mapping(source = "idId", target = "id")
    @Mapping(target = "bomEntryId", ignore = true)
    Material toEntity(MaterialDTO materialDTO);

    default Material fromId(Long id) {
        if (id == null) {
            return null;
        }
        Material material = new Material();
        material.setId(id);
        return material;
    }
}

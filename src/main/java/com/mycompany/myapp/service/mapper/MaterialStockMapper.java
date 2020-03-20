package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MaterialStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MaterialStock} and its DTO {@link MaterialStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MaterialStockMapper extends EntityMapper<MaterialStockDTO, MaterialStock> {


    @Mapping(target = "materialId", ignore = true)
    MaterialStock toEntity(MaterialStockDTO materialStockDTO);

    default MaterialStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        MaterialStock materialStock = new MaterialStock();
        materialStock.setId(id);
        return materialStock;
    }
}

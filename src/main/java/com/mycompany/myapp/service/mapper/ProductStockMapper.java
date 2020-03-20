package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ProductStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductStock} and its DTO {@link ProductStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductStockMapper extends EntityMapper<ProductStockDTO, ProductStock> {


    @Mapping(target = "productId", ignore = true)
    ProductStock toEntity(ProductStockDTO productStockDTO);

    default ProductStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductStock productStock = new ProductStock();
        productStock.setId(id);
        return productStock;
    }
}

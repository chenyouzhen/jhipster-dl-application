package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductStockMapper.class, FactoryMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "id.id", target = "idId")
    @Mapping(source = "factory.id", target = "factoryId")
    @Mapping(source = "factory.name", target = "factoryName")
    ProductDTO toDto(Product product);

    @Mapping(source = "idId", target = "id")
    @Mapping(target = "ids", ignore = true)
    @Mapping(target = "removeId", ignore = true)
    @Mapping(target = "ids", ignore = true)
    @Mapping(target = "removeId", ignore = true)
    @Mapping(target = "assemblylines", ignore = true)
    @Mapping(target = "removeAssemblyline", ignore = true)
    @Mapping(target = "records", ignore = true)
    @Mapping(target = "removeRecord", ignore = true)
    @Mapping(source = "factoryId", target = "factory")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}

package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductStockMapperTest {

    private ProductStockMapper productStockMapper;

    @BeforeEach
    public void setUp() {
        productStockMapper = new ProductStockMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productStockMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(productStockMapper.fromId(null)).isNull();
    }
}

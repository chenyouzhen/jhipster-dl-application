package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MaterialStockMapperTest {

    private MaterialStockMapper materialStockMapper;

    @BeforeEach
    public void setUp() {
        materialStockMapper = new MaterialStockMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(materialStockMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(materialStockMapper.fromId(null)).isNull();
    }
}

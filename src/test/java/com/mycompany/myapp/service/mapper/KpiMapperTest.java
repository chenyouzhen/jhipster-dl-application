package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class KpiMapperTest {

    private KpiMapper kpiMapper;

    @BeforeEach
    public void setUp() {
        kpiMapper = new KpiMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(kpiMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(kpiMapper.fromId(null)).isNull();
    }
}

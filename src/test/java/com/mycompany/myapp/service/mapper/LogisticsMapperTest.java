package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LogisticsMapperTest {

    private LogisticsMapper logisticsMapper;

    @BeforeEach
    public void setUp() {
        logisticsMapper = new LogisticsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(logisticsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(logisticsMapper.fromId(null)).isNull();
    }
}

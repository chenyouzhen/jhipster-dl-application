package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DemandMapperTest {

    private DemandMapper demandMapper;

    @BeforeEach
    public void setUp() {
        demandMapper = new DemandMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(demandMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(demandMapper.fromId(null)).isNull();
    }
}

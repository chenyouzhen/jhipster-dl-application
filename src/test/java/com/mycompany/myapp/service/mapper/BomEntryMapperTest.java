package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BomEntryMapperTest {

    private BomEntryMapper bomEntryMapper;

    @BeforeEach
    public void setUp() {
        bomEntryMapper = new BomEntryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bomEntryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bomEntryMapper.fromId(null)).isNull();
    }
}

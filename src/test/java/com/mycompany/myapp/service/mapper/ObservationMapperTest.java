package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ObservationMapperTest {

    private ObservationMapper observationMapper;

    @BeforeEach
    public void setUp() {
        observationMapper = new ObservationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(observationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(observationMapper.fromId(null)).isNull();
    }
}

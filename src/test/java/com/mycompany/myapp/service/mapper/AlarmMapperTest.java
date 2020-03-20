package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlarmMapperTest {

    private AlarmMapper alarmMapper;

    @BeforeEach
    public void setUp() {
        alarmMapper = new AlarmMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alarmMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alarmMapper.fromId(null)).isNull();
    }
}

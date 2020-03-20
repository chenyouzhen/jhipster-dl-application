package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AssemblyLineMapperTest {

    private AssemblyLineMapper assemblyLineMapper;

    @BeforeEach
    public void setUp() {
        assemblyLineMapper = new AssemblyLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(assemblyLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(assemblyLineMapper.fromId(null)).isNull();
    }
}

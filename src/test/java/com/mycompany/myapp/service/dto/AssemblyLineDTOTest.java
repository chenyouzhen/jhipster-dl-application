package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AssemblyLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssemblyLineDTO.class);
        AssemblyLineDTO assemblyLineDTO1 = new AssemblyLineDTO();
        assemblyLineDTO1.setId(1L);
        AssemblyLineDTO assemblyLineDTO2 = new AssemblyLineDTO();
        assertThat(assemblyLineDTO1).isNotEqualTo(assemblyLineDTO2);
        assemblyLineDTO2.setId(assemblyLineDTO1.getId());
        assertThat(assemblyLineDTO1).isEqualTo(assemblyLineDTO2);
        assemblyLineDTO2.setId(2L);
        assertThat(assemblyLineDTO1).isNotEqualTo(assemblyLineDTO2);
        assemblyLineDTO1.setId(null);
        assertThat(assemblyLineDTO1).isNotEqualTo(assemblyLineDTO2);
    }
}

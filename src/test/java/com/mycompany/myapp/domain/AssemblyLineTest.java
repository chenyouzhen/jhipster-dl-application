package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AssemblyLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssemblyLine.class);
        AssemblyLine assemblyLine1 = new AssemblyLine();
        assemblyLine1.setId(1L);
        AssemblyLine assemblyLine2 = new AssemblyLine();
        assemblyLine2.setId(assemblyLine1.getId());
        assertThat(assemblyLine1).isEqualTo(assemblyLine2);
        assemblyLine2.setId(2L);
        assertThat(assemblyLine1).isNotEqualTo(assemblyLine2);
        assemblyLine1.setId(null);
        assertThat(assemblyLine1).isNotEqualTo(assemblyLine2);
    }
}

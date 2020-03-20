package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class KpiDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KpiDTO.class);
        KpiDTO kpiDTO1 = new KpiDTO();
        kpiDTO1.setId(1L);
        KpiDTO kpiDTO2 = new KpiDTO();
        assertThat(kpiDTO1).isNotEqualTo(kpiDTO2);
        kpiDTO2.setId(kpiDTO1.getId());
        assertThat(kpiDTO1).isEqualTo(kpiDTO2);
        kpiDTO2.setId(2L);
        assertThat(kpiDTO1).isNotEqualTo(kpiDTO2);
        kpiDTO1.setId(null);
        assertThat(kpiDTO1).isNotEqualTo(kpiDTO2);
    }
}

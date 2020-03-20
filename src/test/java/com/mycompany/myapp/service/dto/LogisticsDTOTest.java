package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LogisticsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogisticsDTO.class);
        LogisticsDTO logisticsDTO1 = new LogisticsDTO();
        logisticsDTO1.setId(1L);
        LogisticsDTO logisticsDTO2 = new LogisticsDTO();
        assertThat(logisticsDTO1).isNotEqualTo(logisticsDTO2);
        logisticsDTO2.setId(logisticsDTO1.getId());
        assertThat(logisticsDTO1).isEqualTo(logisticsDTO2);
        logisticsDTO2.setId(2L);
        assertThat(logisticsDTO1).isNotEqualTo(logisticsDTO2);
        logisticsDTO1.setId(null);
        assertThat(logisticsDTO1).isNotEqualTo(logisticsDTO2);
    }
}

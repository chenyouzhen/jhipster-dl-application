package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AlarmDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmDTO.class);
        AlarmDTO alarmDTO1 = new AlarmDTO();
        alarmDTO1.setId(1L);
        AlarmDTO alarmDTO2 = new AlarmDTO();
        assertThat(alarmDTO1).isNotEqualTo(alarmDTO2);
        alarmDTO2.setId(alarmDTO1.getId());
        assertThat(alarmDTO1).isEqualTo(alarmDTO2);
        alarmDTO2.setId(2L);
        assertThat(alarmDTO1).isNotEqualTo(alarmDTO2);
        alarmDTO1.setId(null);
        assertThat(alarmDTO1).isNotEqualTo(alarmDTO2);
    }
}

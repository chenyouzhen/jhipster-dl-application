package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ObservationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservationDTO.class);
        ObservationDTO observationDTO1 = new ObservationDTO();
        observationDTO1.setId(1L);
        ObservationDTO observationDTO2 = new ObservationDTO();
        assertThat(observationDTO1).isNotEqualTo(observationDTO2);
        observationDTO2.setId(observationDTO1.getId());
        assertThat(observationDTO1).isEqualTo(observationDTO2);
        observationDTO2.setId(2L);
        assertThat(observationDTO1).isNotEqualTo(observationDTO2);
        observationDTO1.setId(null);
        assertThat(observationDTO1).isNotEqualTo(observationDTO2);
    }
}

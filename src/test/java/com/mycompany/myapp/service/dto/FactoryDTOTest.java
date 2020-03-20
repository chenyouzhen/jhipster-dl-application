package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class FactoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactoryDTO.class);
        FactoryDTO factoryDTO1 = new FactoryDTO();
        factoryDTO1.setId(1L);
        FactoryDTO factoryDTO2 = new FactoryDTO();
        assertThat(factoryDTO1).isNotEqualTo(factoryDTO2);
        factoryDTO2.setId(factoryDTO1.getId());
        assertThat(factoryDTO1).isEqualTo(factoryDTO2);
        factoryDTO2.setId(2L);
        assertThat(factoryDTO1).isNotEqualTo(factoryDTO2);
        factoryDTO1.setId(null);
        assertThat(factoryDTO1).isNotEqualTo(factoryDTO2);
    }
}

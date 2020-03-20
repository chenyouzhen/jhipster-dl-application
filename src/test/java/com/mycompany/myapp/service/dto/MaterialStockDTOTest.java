package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class MaterialStockDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialStockDTO.class);
        MaterialStockDTO materialStockDTO1 = new MaterialStockDTO();
        materialStockDTO1.setId(1L);
        MaterialStockDTO materialStockDTO2 = new MaterialStockDTO();
        assertThat(materialStockDTO1).isNotEqualTo(materialStockDTO2);
        materialStockDTO2.setId(materialStockDTO1.getId());
        assertThat(materialStockDTO1).isEqualTo(materialStockDTO2);
        materialStockDTO2.setId(2L);
        assertThat(materialStockDTO1).isNotEqualTo(materialStockDTO2);
        materialStockDTO1.setId(null);
        assertThat(materialStockDTO1).isNotEqualTo(materialStockDTO2);
    }
}

package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProductStockDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductStockDTO.class);
        ProductStockDTO productStockDTO1 = new ProductStockDTO();
        productStockDTO1.setId(1L);
        ProductStockDTO productStockDTO2 = new ProductStockDTO();
        assertThat(productStockDTO1).isNotEqualTo(productStockDTO2);
        productStockDTO2.setId(productStockDTO1.getId());
        assertThat(productStockDTO1).isEqualTo(productStockDTO2);
        productStockDTO2.setId(2L);
        assertThat(productStockDTO1).isNotEqualTo(productStockDTO2);
        productStockDTO1.setId(null);
        assertThat(productStockDTO1).isNotEqualTo(productStockDTO2);
    }
}

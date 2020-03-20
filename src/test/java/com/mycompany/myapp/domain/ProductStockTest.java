package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ProductStockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductStock.class);
        ProductStock productStock1 = new ProductStock();
        productStock1.setId(1L);
        ProductStock productStock2 = new ProductStock();
        productStock2.setId(productStock1.getId());
        assertThat(productStock1).isEqualTo(productStock2);
        productStock2.setId(2L);
        assertThat(productStock1).isNotEqualTo(productStock2);
        productStock1.setId(null);
        assertThat(productStock1).isNotEqualTo(productStock2);
    }
}

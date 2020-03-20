package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class MaterialStockTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialStock.class);
        MaterialStock materialStock1 = new MaterialStock();
        materialStock1.setId(1L);
        MaterialStock materialStock2 = new MaterialStock();
        materialStock2.setId(materialStock1.getId());
        assertThat(materialStock1).isEqualTo(materialStock2);
        materialStock2.setId(2L);
        assertThat(materialStock1).isNotEqualTo(materialStock2);
        materialStock1.setId(null);
        assertThat(materialStock1).isNotEqualTo(materialStock2);
    }
}

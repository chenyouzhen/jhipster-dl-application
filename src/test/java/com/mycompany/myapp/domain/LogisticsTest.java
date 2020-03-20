package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LogisticsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Logistics.class);
        Logistics logistics1 = new Logistics();
        logistics1.setId(1L);
        Logistics logistics2 = new Logistics();
        logistics2.setId(logistics1.getId());
        assertThat(logistics1).isEqualTo(logistics2);
        logistics2.setId(2L);
        assertThat(logistics1).isNotEqualTo(logistics2);
        logistics1.setId(null);
        assertThat(logistics1).isNotEqualTo(logistics2);
    }
}

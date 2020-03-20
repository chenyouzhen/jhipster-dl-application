package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class FactoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Factory.class);
        Factory factory1 = new Factory();
        factory1.setId(1L);
        Factory factory2 = new Factory();
        factory2.setId(factory1.getId());
        assertThat(factory1).isEqualTo(factory2);
        factory2.setId(2L);
        assertThat(factory1).isNotEqualTo(factory2);
        factory1.setId(null);
        assertThat(factory1).isNotEqualTo(factory2);
    }
}

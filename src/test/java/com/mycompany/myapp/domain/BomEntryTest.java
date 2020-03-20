package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BomEntryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BomEntry.class);
        BomEntry bomEntry1 = new BomEntry();
        bomEntry1.setId(1L);
        BomEntry bomEntry2 = new BomEntry();
        bomEntry2.setId(bomEntry1.getId());
        assertThat(bomEntry1).isEqualTo(bomEntry2);
        bomEntry2.setId(2L);
        assertThat(bomEntry1).isNotEqualTo(bomEntry2);
        bomEntry1.setId(null);
        assertThat(bomEntry1).isNotEqualTo(bomEntry2);
    }
}

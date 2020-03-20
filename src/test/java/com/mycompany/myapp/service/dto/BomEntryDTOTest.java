package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BomEntryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BomEntryDTO.class);
        BomEntryDTO bomEntryDTO1 = new BomEntryDTO();
        bomEntryDTO1.setId(1L);
        BomEntryDTO bomEntryDTO2 = new BomEntryDTO();
        assertThat(bomEntryDTO1).isNotEqualTo(bomEntryDTO2);
        bomEntryDTO2.setId(bomEntryDTO1.getId());
        assertThat(bomEntryDTO1).isEqualTo(bomEntryDTO2);
        bomEntryDTO2.setId(2L);
        assertThat(bomEntryDTO1).isNotEqualTo(bomEntryDTO2);
        bomEntryDTO1.setId(null);
        assertThat(bomEntryDTO1).isNotEqualTo(bomEntryDTO2);
    }
}

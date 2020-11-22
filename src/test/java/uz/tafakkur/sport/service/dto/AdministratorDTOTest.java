package uz.tafakkur.sport.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.tafakkur.sport.web.rest.TestUtil;

public class AdministratorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministratorDTO.class);
        AdministratorDTO administratorDTO1 = new AdministratorDTO();
        administratorDTO1.setId(1L);
        AdministratorDTO administratorDTO2 = new AdministratorDTO();
        assertThat(administratorDTO1).isNotEqualTo(administratorDTO2);
        administratorDTO2.setId(administratorDTO1.getId());
        assertThat(administratorDTO1).isEqualTo(administratorDTO2);
        administratorDTO2.setId(2L);
        assertThat(administratorDTO1).isNotEqualTo(administratorDTO2);
        administratorDTO1.setId(null);
        assertThat(administratorDTO1).isNotEqualTo(administratorDTO2);
    }
}

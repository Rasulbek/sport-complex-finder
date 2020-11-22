package uz.tafakkur.sport.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.tafakkur.sport.web.rest.TestUtil;

public class SportsHallDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SportsHallDTO.class);
        SportsHallDTO sportsHallDTO1 = new SportsHallDTO();
        sportsHallDTO1.setId(1L);
        SportsHallDTO sportsHallDTO2 = new SportsHallDTO();
        assertThat(sportsHallDTO1).isNotEqualTo(sportsHallDTO2);
        sportsHallDTO2.setId(sportsHallDTO1.getId());
        assertThat(sportsHallDTO1).isEqualTo(sportsHallDTO2);
        sportsHallDTO2.setId(2L);
        assertThat(sportsHallDTO1).isNotEqualTo(sportsHallDTO2);
        sportsHallDTO1.setId(null);
        assertThat(sportsHallDTO1).isNotEqualTo(sportsHallDTO2);
    }
}

package uz.tafakkur.sport.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.tafakkur.sport.web.rest.TestUtil;

public class SportsHallTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SportsHall.class);
        SportsHall sportsHall1 = new SportsHall();
        sportsHall1.setId(1L);
        SportsHall sportsHall2 = new SportsHall();
        sportsHall2.setId(sportsHall1.getId());
        assertThat(sportsHall1).isEqualTo(sportsHall2);
        sportsHall2.setId(2L);
        assertThat(sportsHall1).isNotEqualTo(sportsHall2);
        sportsHall1.setId(null);
        assertThat(sportsHall1).isNotEqualTo(sportsHall2);
    }
}

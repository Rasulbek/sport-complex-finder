package uz.tafakkur.sport.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SportsHallMapperTest {
    private SportsHallMapper sportsHallMapper;

    @BeforeEach
    public void setUp() {
        sportsHallMapper = new SportsHallMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sportsHallMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sportsHallMapper.fromId(null)).isNull();
    }
}

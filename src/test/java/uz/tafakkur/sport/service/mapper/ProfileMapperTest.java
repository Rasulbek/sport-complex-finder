package uz.tafakkur.sport.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProfileMapperTest {
    private ProfileMapper profileMapper;

    @BeforeEach
    public void setUp() {
        profileMapper = new ProfileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(profileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(profileMapper.fromId(null)).isNull();
    }
}

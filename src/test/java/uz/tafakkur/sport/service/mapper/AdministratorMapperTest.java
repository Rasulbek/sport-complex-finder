package uz.tafakkur.sport.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdministratorMapperTest {
    private AdministratorMapper administratorMapper;

    @BeforeEach
    public void setUp() {
        administratorMapper = new AdministratorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(administratorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(administratorMapper.fromId(null)).isNull();
    }
}

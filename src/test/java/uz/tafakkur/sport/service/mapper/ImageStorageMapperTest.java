package uz.tafakkur.sport.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImageStorageMapperTest {
    private ImageStorageMapper imageStorageMapper;

    @BeforeEach
    public void setUp() {
        imageStorageMapper = new ImageStorageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(imageStorageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(imageStorageMapper.fromId(null)).isNull();
    }
}

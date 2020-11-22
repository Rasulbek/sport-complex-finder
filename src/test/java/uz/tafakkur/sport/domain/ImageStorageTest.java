package uz.tafakkur.sport.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.tafakkur.sport.web.rest.TestUtil;

public class ImageStorageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageStorage.class);
        ImageStorage imageStorage1 = new ImageStorage();
        imageStorage1.setId(1L);
        ImageStorage imageStorage2 = new ImageStorage();
        imageStorage2.setId(imageStorage1.getId());
        assertThat(imageStorage1).isEqualTo(imageStorage2);
        imageStorage2.setId(2L);
        assertThat(imageStorage1).isNotEqualTo(imageStorage2);
        imageStorage1.setId(null);
        assertThat(imageStorage1).isNotEqualTo(imageStorage2);
    }
}

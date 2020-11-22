package uz.tafakkur.sport.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.tafakkur.sport.web.rest.TestUtil;

public class ImageStorageDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageStorageDTO.class);
        ImageStorageDTO imageStorageDTO1 = new ImageStorageDTO();
        imageStorageDTO1.setId(1L);
        ImageStorageDTO imageStorageDTO2 = new ImageStorageDTO();
        assertThat(imageStorageDTO1).isNotEqualTo(imageStorageDTO2);
        imageStorageDTO2.setId(imageStorageDTO1.getId());
        assertThat(imageStorageDTO1).isEqualTo(imageStorageDTO2);
        imageStorageDTO2.setId(2L);
        assertThat(imageStorageDTO1).isNotEqualTo(imageStorageDTO2);
        imageStorageDTO1.setId(null);
        assertThat(imageStorageDTO1).isNotEqualTo(imageStorageDTO2);
    }
}

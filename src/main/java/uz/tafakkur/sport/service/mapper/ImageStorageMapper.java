package uz.tafakkur.sport.service.mapper;

import org.mapstruct.*;
import uz.tafakkur.sport.domain.*;
import uz.tafakkur.sport.service.dto.ImageStorageDTO;

/**
 * Mapper for the entity {@link ImageStorage} and its DTO {@link ImageStorageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImageStorageMapper extends EntityMapper<ImageStorageDTO, ImageStorage> {
    default ImageStorage fromId(Long id) {
        if (id == null) {
            return null;
        }
        ImageStorage imageStorage = new ImageStorage();
        imageStorage.setId(id);
        return imageStorage;
    }
}

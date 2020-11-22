package uz.tafakkur.sport.service.mapper;

import org.mapstruct.*;
import uz.tafakkur.sport.domain.*;
import uz.tafakkur.sport.service.dto.ProfileDTO;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = { CityMapper.class })
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "cityId", target = "city")
    Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}

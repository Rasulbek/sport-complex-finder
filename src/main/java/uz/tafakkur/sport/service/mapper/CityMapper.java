package uz.tafakkur.sport.service.mapper;

import org.mapstruct.*;
import uz.tafakkur.sport.domain.*;
import uz.tafakkur.sport.service.dto.CityDTO;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring", uses = { RegionMapper.class })
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    CityDTO toDto(City city);

    @Mapping(source = "regionId", target = "region")
    City toEntity(CityDTO cityDTO);

    default City fromId(Long id) {
        if (id == null) {
            return null;
        }
        City city = new City();
        city.setId(id);
        return city;
    }
}

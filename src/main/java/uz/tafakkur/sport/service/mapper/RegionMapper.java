package uz.tafakkur.sport.service.mapper;

import org.mapstruct.*;
import uz.tafakkur.sport.domain.*;
import uz.tafakkur.sport.service.dto.RegionDTO;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {
    default Region fromId(Long id) {
        if (id == null) {
            return null;
        }
        Region region = new Region();
        region.setId(id);
        return region;
    }
}

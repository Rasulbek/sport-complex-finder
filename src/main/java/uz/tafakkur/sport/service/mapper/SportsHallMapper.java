package uz.tafakkur.sport.service.mapper;

import org.mapstruct.*;
import uz.tafakkur.sport.domain.*;
import uz.tafakkur.sport.service.dto.SportsHallDTO;

/**
 * Mapper for the entity {@link SportsHall} and its DTO {@link SportsHallDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategoryMapper.class, CityMapper.class })
public interface SportsHallMapper extends EntityMapper<SportsHallDTO, SportsHall> {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    SportsHallDTO toDto(SportsHall sportsHall);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "cityId", target = "city")
    SportsHall toEntity(SportsHallDTO sportsHallDTO);

    default SportsHall fromId(Long id) {
        if (id == null) {
            return null;
        }
        SportsHall sportsHall = new SportsHall();
        sportsHall.setId(id);
        return sportsHall;
    }
}

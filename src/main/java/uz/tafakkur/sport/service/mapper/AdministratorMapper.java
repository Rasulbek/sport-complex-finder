package uz.tafakkur.sport.service.mapper;

import org.mapstruct.*;
import uz.tafakkur.sport.domain.*;
import uz.tafakkur.sport.service.dto.AdministratorDTO;

/**
 * Mapper for the entity {@link Administrator} and its DTO {@link AdministratorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdministratorMapper extends EntityMapper<AdministratorDTO, Administrator> {
    default Administrator fromId(Long id) {
        if (id == null) {
            return null;
        }
        Administrator administrator = new Administrator();
        administrator.setId(id);
        return administrator;
    }
}

package uz.tafakkur.sport.service.mapper;

import org.mapstruct.*;
import uz.tafakkur.sport.domain.*;
import uz.tafakkur.sport.service.dto.CategoryDTO;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}

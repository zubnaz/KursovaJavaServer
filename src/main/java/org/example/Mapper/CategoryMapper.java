package org.example.Mapper;

import org.example.DTO.Category.CategoryItemDto;
import org.example.Entities.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryItemDto categoryItemDto(CategoryEntity categoryEntity);
    List<CategoryItemDto> categoryListItemDto(List<CategoryEntity> listCategoryEntity);
}

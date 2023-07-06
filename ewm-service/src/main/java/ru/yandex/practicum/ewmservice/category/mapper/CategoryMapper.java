package ru.yandex.practicum.ewmservice.category.mapper;

import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.yandex.practicum.ewmservice.category.model.Category;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category inCategoryDto(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

    public static NewCategoryDto toNewCategoryDto(Category category) {
        return NewCategoryDto.builder()
                .name(category.getName())
                .build();
    }

    public static Category inNewCategoryDto(NewCategoryDto newCategoryDto) {
        return Category.builder()

                .name(newCategoryDto.getName())
                .build();
    }
}

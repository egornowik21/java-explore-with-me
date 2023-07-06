package ru.yandex.practicum.ewmservice.category.service;

import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);

    CategoryDto postCategory(NewCategoryDto newCategoryDto);

    CategoryDto patchCategory(Long id, CategoryDto categoryDto);

    void deleteCategoryById(Long catId);
}

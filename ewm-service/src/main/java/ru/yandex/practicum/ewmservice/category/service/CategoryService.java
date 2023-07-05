package ru.yandex.practicum.ewmservice.category.service;

import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories(Integer from, Integer size);
    CategoryDto getCategoryById(Long catId);
}

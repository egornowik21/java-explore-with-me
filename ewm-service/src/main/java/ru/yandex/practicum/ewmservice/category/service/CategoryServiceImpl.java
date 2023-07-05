package ru.yandex.practicum.ewmservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewmservice.category.dao.CategoryRepository;
import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<Category> categoryDtoList = categoryRepository.findAll(pageable);
        return categoryDtoList
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория не найдена"));
        return CategoryMapper.toCategoryDto(category);
    }

}

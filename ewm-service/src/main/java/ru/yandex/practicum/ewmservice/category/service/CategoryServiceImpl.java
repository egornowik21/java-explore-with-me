package ru.yandex.practicum.ewmservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.ewmservice.category.dao.CategoryRepository;
import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.yandex.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.event.dao.EventRepository;
import ru.yandex.practicum.ewmservice.exception.ConflictException;
import ru.yandex.practicum.ewmservice.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

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


    @Override
    @Transactional
    public CategoryDto postCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.inNewCategoryDto(newCategoryDto);
        if (categoryRepository.findByName(newCategoryDto.getName()).size() > 0) {
            throw new ConflictException("Имя категории уже существует");
        }
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto patchCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория не найдена"));
        if (categoryDto.getName() == null) {
            category.setName(category.getName());
        } else {
            category.setName(categoryDto.getName());
        }
        categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long catId) {
        if (eventRepository.findAllByCategory_Id(catId).size() > 0) {
            throw new ConflictException("Есть связанные события для этой категори");
        }
        categoryRepository.deleteById(catId);
    }

}

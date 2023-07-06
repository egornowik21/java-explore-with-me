package ru.yandex.practicum.ewmservice.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.category.dto.NewCategoryDto;
import ru.yandex.practicum.ewmservice.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto saveNewCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("POST/categories - admin post category.");
        return categoryService.postCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable long catId) {
        log.info("DELETE/categories - admin delete category.");
        categoryService.deleteCategoryById(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("catId") Long catId) {
        log.info("PATCH/categories - admin patch category");
        return categoryService.patchCategory(catId, categoryDto);
    }
}

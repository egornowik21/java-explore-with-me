package ru.yandex.practicum.ewmservice.category.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.user.model.User;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Page<Category> findAll(Pageable pageable);
}

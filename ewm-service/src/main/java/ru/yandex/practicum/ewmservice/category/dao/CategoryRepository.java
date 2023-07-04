package ru.yandex.practicum.ewmservice.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.ewmservice.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}

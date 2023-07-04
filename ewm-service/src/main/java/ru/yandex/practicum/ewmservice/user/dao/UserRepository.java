package ru.yandex.practicum.ewmservice.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.ewmservice.user.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    Page<User> findAllById(Long userId, Pageable pageable);
    Page<User> findAll(Pageable pageable);
}

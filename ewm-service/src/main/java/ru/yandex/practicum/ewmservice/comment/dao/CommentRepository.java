package ru.yandex.practicum.ewmservice.comment.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.ewmservice.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEventId(Long eventId, Pageable pageable);

    List<Comment> findByAuthor_id(Long userId, Pageable pageable);
}

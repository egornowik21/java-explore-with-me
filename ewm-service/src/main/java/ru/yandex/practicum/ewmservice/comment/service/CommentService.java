package ru.yandex.practicum.ewmservice.comment.service;

import ru.yandex.practicum.ewmservice.comment.dto.CommentDto;
import ru.yandex.practicum.ewmservice.comment.dto.NewComment;

import java.util.List;

public interface CommentService {
    CommentDto postCommentByEvent(Long userId, NewComment newComment, Long eventId);
    List<CommentDto> getAllCommentsUser(Long userId, Integer from, Integer size);
}

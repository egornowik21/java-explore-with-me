package ru.yandex.practicum.ewmservice.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewmservice.comment.dao.CommentRepository;
import ru.yandex.practicum.ewmservice.comment.dto.CommentDto;
import ru.yandex.practicum.ewmservice.comment.dto.NewComment;
import ru.yandex.practicum.ewmservice.comment.mapper.CommentMapper;
import ru.yandex.practicum.ewmservice.comment.model.Comment;
import ru.yandex.practicum.ewmservice.event.dao.EventRepository;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.event.model.State;
import ru.yandex.practicum.ewmservice.exception.ConflictException;
import ru.yandex.practicum.ewmservice.exception.NotFoundException;
import ru.yandex.practicum.ewmservice.exception.ValidationException;
import ru.yandex.practicum.ewmservice.user.dao.UserRepository;
import ru.yandex.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    @Override
    public CommentDto postCommentByEvent(Long userId, NewComment newComment, Long eventId) {
        if (newComment.getText().isBlank() || newComment.getText().isEmpty()) {
            throw new ValidationException("Текст комментария пустой");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictException("Комментарий можно оставить только к опубликованному событию");
        }
        Comment comment = CommentMapper.inNewComment(newComment, event, user);
        comment.setCreated(LocalDateTime.now());
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getAllCommentsUser(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        List<Comment> commentsList = commentRepository.findByAuthor_id(user.getId(), pageable);
        return commentsList.stream()
                .map(comment -> CommentMapper.toCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto patchComment(Long commentId, Long userId, NewComment newComment) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Комментарий не найден"));
        if (!comment.getAuthor().getId().equals(user.getId())) {
            throw new ConflictException("Пользователь не является создателем комментария");
        }
        comment.setText(newComment.getText());
        comment.setCreated(LocalDateTime.now());
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentByUser(Long userId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Комментарий не найден"));
        if (!comment.getAuthor().getId().equals(user.getId())) {
            throw new ConflictException("Пользователь не является создателем комментария");
        }
        commentRepository.deleteById(commentId);
    }
    @Override
    public void deleteCommentByAdmin(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Комментарий не найден"));
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getAllCommentsEvent(Long eventId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Событие не найдено"));
        List<Comment> commentsList = commentRepository.findByEventId(event.getId(), pageable);
        return commentsList.stream()
                .map(comment -> CommentMapper.toCommentDto(comment))
                .collect(Collectors.toList());
    }


}

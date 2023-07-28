package ru.yandex.practicum.ewmservice.comment.mapper;

import ru.yandex.practicum.ewmservice.comment.dto.CommentDto;
import ru.yandex.practicum.ewmservice.comment.dto.NewComment;
import ru.yandex.practicum.ewmservice.comment.model.Comment;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public static Comment inCommentDto(CommentDto commentDto, Event event, User user) {
        return Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .author(user)
                .event(event)
                .created(commentDto.getCreated())
                .build();
    }

    public static Comment inNewComment(NewComment newComment, Event event, User user) {
        return Comment.builder()
                .text(newComment.getText())
                .author(user)
                .event(event)
                .created(null)
                .build();
    }
}

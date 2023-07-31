package ru.yandex.practicum.ewmservice.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.comment.dto.CommentDto;
import ru.yandex.practicum.ewmservice.comment.dto.NewComment;
import ru.yandex.practicum.ewmservice.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @PostMapping(path = "/users/{userId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestParam Long eventId,
                                    @PathVariable("userId") Long userId,
                                    @Valid @RequestBody NewComment newComment) {
        log.info("POST/comments - добавлен новый комментарий к событию");
        return commentService.postCommentByEvent(userId, newComment, eventId);
    }

    @GetMapping(path = "/users/{userId}/comment")
    public List<CommentDto> getCommentsByUser(@PathVariable("userId") Long userId,
                                              @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                              @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        log.info("GET/comments - получен список всех комментариев пользователя");
        return commentService.getAllCommentsUser(userId, from, size);
    }

    @PatchMapping(path = "/users/{userId}/comment/{comId}")
    public CommentDto patchComment(@PathVariable("userId") Long userId,
                                   @PathVariable("comId") Long comId,
                                   @Valid @RequestBody NewComment newComment) {
        log.info("PATCH/comment - обновлен комментарий пользователя");
        return commentService.patchComment(comId, userId, newComment);
    }

    @DeleteMapping(path = "/users/{userId}/comment/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long userId, @PathVariable("comId") Long comId) {
        log.info("DELETE/comments - удален текущий комментарий пользователя.");
        commentService.deleteCommentByUser(userId, comId);
    }

    @DeleteMapping(path = "/admin/comment/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentByAdmin(@PathVariable("comId") Long comId) {
        log.info("DELETE/comments - удален текущий комментарий администратором.");
        commentService.deleteCommentByAdmin(comId);
    }

    @GetMapping(path = "/events/{eventId}/comment")
    public List<CommentDto> getCommentsByEvent(@PathVariable("eventId") Long eventId,
                                               @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                               @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        log.info("GET/comments - получен список всех комментариев для события");
        return commentService.getAllCommentsEvent(eventId, from, size);
    }
}

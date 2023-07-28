package ru.yandex.practicum.ewmservice.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.comment.dto.CommentDto;
import ru.yandex.practicum.ewmservice.comment.dto.NewComment;
import ru.yandex.practicum.ewmservice.comment.service.CommentService;

import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users/{userId}/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestParam Long eventId,
                                    @PathVariable("userId") Long userId,
                                    @RequestBody NewComment newComment) {
        log.info("POST/comments - добавлен новый комментарий к событию");
        return commentService.postCommentByEvent(userId, newComment, eventId);
    }

    @GetMapping
    public List<CommentDto> getComments(@PathVariable("userId") Long userId,
                                        @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                        @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        log.info("GET/comments - получен список всех комментариев пользователя");
        return commentService.getAllCommentsUser(userId, from, size);
    }
}

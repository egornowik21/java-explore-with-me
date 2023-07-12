package ru.yandex.practicum.ewmservice.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.ewmservice.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestService requestService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto saveRequest(@PathVariable("userId") Long userId,
                                               @RequestParam("eventId") Long eventId) {
        log.info("POST/requests - добавлен новый запрос.");
        return requestService.postRequest(userId, eventId);
    }

    @GetMapping
    public List<ParticipationRequestDto> getRequestList(@PathVariable("userId") Long userId) {
        log.info("GET/requests - получен список всех запросов пользователя.");
        return requestService.requestList(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto patchRequest(@PathVariable("userId") Long userId,
                                                @PathVariable("requestId") Long requestId) {
        log.info("PATCH/requests - запрос отмене");
        return requestService.patchRequest(userId, requestId);
    }
}

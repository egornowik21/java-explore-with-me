package ru.yandex.practicum.ewmservice.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.request.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.ewmservice.request.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.ewmservice.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto saveRequest(@PathVariable("userId") Long userId,
                                               @RequestParam("eventId") Long eventId) {
        log.info("POST/requests - добавлен новый запрос.");
        return requestService.postRequest(userId, eventId);
    }

    @GetMapping("/requests")
    public List<ParticipationRequestDto> getRequestList(@PathVariable("userId") Long userId) {
        log.info("GET/requests - получен список всех запросов пользователя.");
        return requestService.requestList(userId);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto patchRequest(@PathVariable("userId") Long userId,
                                                @PathVariable("requestId") Long requestId) {
        log.info("PATCH/requests - запрос отмене");
        return requestService.patchRequest(userId, requestId);
    }

    @GetMapping("events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequestList(@PathVariable("userId") Long userId,
                                                             @PathVariable("eventId") Long eventId) {
        log.info("GET/requests - получен список всех запросов события.");
        return requestService.getEventRequestList(userId, eventId);
    }

    @PatchMapping("events/{eventId}/requests")
    public EventRequestStatusUpdateResult pathEventRequest(@PathVariable("userId") Long userId,
                                                           @PathVariable("eventId") Long eventId,
                                                           @RequestBody EventRequestStatusUpdateRequest
                                                                   eventRequestStatusUpdateRequest
    ) {
        log.info("PATCH/requests - изменен статус заявок на участие в событии");
        return requestService.patchEventRequest(userId, eventId, eventRequestStatusUpdateRequest);
    }

}

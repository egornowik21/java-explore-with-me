package ru.yandex.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.event.dto.EventFullDto;
import ru.yandex.practicum.ewmservice.event.dto.NewEventDto;
import ru.yandex.practicum.ewmservice.event.service.EventService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventController {

    private final EventService eventService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto saveEvent(@PathVariable("userId")Long userId,
                                  @Valid @RequestBody NewEventDto newEventDto) {
        log.info("POST/events - добавлено новое событие.");
        return eventService.postEvent(userId, newEventDto);
    }


}

package ru.yandex.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.event.dto.EventFullDto;
import ru.yandex.practicum.ewmservice.event.dto.EventShortDto;
import ru.yandex.practicum.ewmservice.event.dto.NewEventDto;
import ru.yandex.practicum.ewmservice.event.dto.UpdateEventUserRequest;
import ru.yandex.practicum.ewmservice.event.service.EventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventController {

    private final EventService eventService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto saveEvent(@PathVariable("userId") Long userId,
                                  @Valid @RequestBody NewEventDto newEventDto) {
        log.info("POST/events - добавлено новое событие.");
        return eventService.postEvent(userId, newEventDto);
    }

    @GetMapping
    public List<EventShortDto> getEventsListByUserId(@PathVariable("userId") Long userId,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET/events - получен список событий");
        return eventService.getAllEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventsByUserId(@PathVariable("userId") Long userId,
                                          @PathVariable("eventId") Long eventId) {
        log.info("GET/events - получено событие по ID");
        return eventService.getEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable("userId") Long userId,
                                   @PathVariable("eventId") Long eventId,
                                   @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("GET/events - обновлено событие по ID");
        return eventService.patchEvent(userId, eventId, updateEventUserRequest);
    }


}

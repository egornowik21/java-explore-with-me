package ru.yandex.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.ewmservice.event.dto.EventFullDto;
import ru.yandex.practicum.ewmservice.event.service.EventService;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
public class PublicEventController {
    private final EventService eventService;

    @GetMapping("/{eventId}")
    public EventFullDto getPublicEvent(@PathVariable("eventId") Long eventId) {
        log.info("GET/events - получено опубликованное событие по ID");
        return eventService.getPublicEventById(eventId);
    }

}

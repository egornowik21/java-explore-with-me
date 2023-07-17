package ru.yandex.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.event.dto.EventFullDto;
import ru.yandex.practicum.ewmservice.event.dto.EventShortDto;
import ru.yandex.practicum.ewmservice.event.model.EventSortType;
import ru.yandex.practicum.ewmservice.event.service.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
public class PublicEventController {
    private final EventService eventService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/{eventId}")
    public EventFullDto getPublicEvent(@PathVariable("eventId") Long eventId) {
        log.info("GET/events - получено опубликованное событие по ID");
        return eventService.getPublicEventById(eventId);
    }

    @GetMapping()
    public List<EventShortDto> getPublicEventList(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) List<Long> categories,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false) String rangeStart,
                                                  @RequestParam(required = false) String rangeEnd,
                                                  @RequestParam(required = false) Boolean onlyAvailable,
                                                  @RequestParam(required = false) EventSortType sort,
                                                  @RequestParam(defaultValue = "0") Integer from,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET/events - получен список событий с параметрами фильтрации");
        LocalDateTime startsTime = LocalDateTime.parse(rangeStart, FORMATTER);
        LocalDateTime endsTime = LocalDateTime.parse(rangeEnd, FORMATTER);
        return eventService.getPublicEventList(text, categories, paid, startsTime, endsTime, onlyAvailable, sort, from, size);
    }

}

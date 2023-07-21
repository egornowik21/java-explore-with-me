package ru.yandex.practicum.ewmservice.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.event.dto.EventFullDto;
import ru.yandex.practicum.ewmservice.event.dto.UpdateAdminRequest;
import ru.yandex.practicum.ewmservice.event.model.State;
import ru.yandex.practicum.ewmservice.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Slf4j
public class AdminEventController {

    private final EventService eventService;
    @GetMapping
    public List<EventFullDto> getEventsListByUserId(@RequestParam(required = false) List<Long> users,
                                                    @RequestParam(required = false) List<State> states,
                                                    @RequestParam(required = false) List<Long> categories,
                                                    @RequestParam(required = false) String rangeStart,
                                                    @RequestParam(required = false) String rangeEnd,
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET/events - получен список событий для админа");
        return eventService.getAdminEventList(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventAdmin(@PathVariable("eventId") Long eventId,
                                         @Valid @RequestBody UpdateAdminRequest updateAdminRequest) {
        return eventService.updateEventAdmin(eventId, updateAdminRequest);
    }
}

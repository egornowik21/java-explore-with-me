package ru.yandex.practicum.ewmservice.event.service;

import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.event.dto.*;
import ru.yandex.practicum.ewmservice.event.model.EventSortType;
import ru.yandex.practicum.ewmservice.event.model.State;
import ru.yandex.practicum.ewmservice.user.model.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto postEvent(Long userId, NewEventDto newEventDto);
    List<EventShortDto> getAllEvents(Long userId, Integer from, Integer size);
    EventFullDto getEventById(Long userId, Long eventId);
    EventFullDto patchEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);
    List<EventFullDto> getAdminEventList(List<Long> users,
                                         List<State> states,
                                         List<Long> categories,
                                         LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd,
                                         Integer from,
                                         Integer size);
    EventFullDto updateEventAdmin(Long eventId, UpdateAdminRequest updateAdminRequest);
    EventFullDto getPublicEventById(Long eventId, HttpServletRequest request);
    List<EventShortDto> getPublicEventList(String text,
                                           List<Long> categories,
                                           Boolean paid,
                                           LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd,
                                           Boolean onlyAvailable,
                                           EventSortType sort,
                                           Integer from,
                                           Integer size);
}

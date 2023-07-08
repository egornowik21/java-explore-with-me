package ru.yandex.practicum.ewmservice.event.service;

import ru.yandex.practicum.ewmservice.event.dto.EventFullDto;
import ru.yandex.practicum.ewmservice.event.dto.NewEventDto;

public interface EventService {
    EventFullDto postEvent(Long userId, NewEventDto newEventDto);
}

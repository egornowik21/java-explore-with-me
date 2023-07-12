package ru.yandex.practicum.ewmservice.request.service;

import ru.yandex.practicum.ewmservice.request.dto.ParticipationRequestDto;

public interface RequestService {
    ParticipationRequestDto postRequest(Long userId, Long eventId);
}

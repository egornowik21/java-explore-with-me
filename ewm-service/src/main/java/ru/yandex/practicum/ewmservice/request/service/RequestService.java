package ru.yandex.practicum.ewmservice.request.service;

import ru.yandex.practicum.ewmservice.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto postRequest(Long userId, Long eventId);
    List<ParticipationRequestDto> requestList(Long userId);
    ParticipationRequestDto patchRequest(Long userId, Long requestId);
}

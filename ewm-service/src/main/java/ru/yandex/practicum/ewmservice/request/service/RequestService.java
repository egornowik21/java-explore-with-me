package ru.yandex.practicum.ewmservice.request.service;

import ru.yandex.practicum.ewmservice.request.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.ewmservice.request.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.ewmservice.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto postRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> requestList(Long userId);

    ParticipationRequestDto patchRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getEventRequestList(Long userId, Long eventId);

    EventRequestStatusUpdateResult patchEventRequest(Long userId,
                                                     Long eventId,
                                                     EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}

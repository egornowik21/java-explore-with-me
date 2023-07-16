package ru.yandex.practicum.ewmservice.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewmservice.event.dao.EventRepository;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.event.model.State;
import ru.yandex.practicum.ewmservice.exception.ConflictException;
import ru.yandex.practicum.ewmservice.exception.NotFoundException;
import ru.yandex.practicum.ewmservice.request.dao.RequestRepository;
import ru.yandex.practicum.ewmservice.request.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.ewmservice.request.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.ewmservice.request.mapper.RequestMapper;
import ru.yandex.practicum.ewmservice.request.model.Request;
import ru.yandex.practicum.ewmservice.request.model.Status;
import ru.yandex.practicum.ewmservice.user.dao.UserRepository;
import ru.yandex.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public ParticipationRequestDto postRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Event event = eventRepository.findById(eventId).
                orElseThrow(() -> new NotFoundException("Событие не найдено"));
        List<Request> requestList = requestRepository.findByEventIdAndRequesterId(user.getId(), event.getId());
        if (requestList.size() > 0) {
            throw new ConflictException("Нельзя оставить повторный запрос");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Событие должно быть опубликовано");
        }
        if (event.getInitiator().getId().equals(user.getId())) {
            throw new ConflictException("Инициатор не может отправлять запросы");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipants().size() > event.getParticipantLimit()) {
            throw new ConflictException("Превышен лимит запросов на событие");
        }
        Request request = Request.builder()
                .event(event)
                .requester(user)
                .created(LocalDateTime.now())
                .build();
        if (event.getRequestModeration()) {
            request.setStatus(Status.PENDING);
        } else {
            request.setStatus(Status.CONFIRMED);
        }
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> requestList(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        List<Request> requestList = requestRepository.findByRequesterId(user.getId());
        return requestList.stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto patchRequest(Long userId, Long requestId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Request request = requestRepository.findById(requestId).
                orElseThrow(() -> new NotFoundException("Запрос не найден"));
        if (!user.getId().equals(request.getRequester().getId())) {
            throw new ConflictException("Это не запрос пользователя");
        }
        request.setStatus(Status.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getEventRequestList(Long userId, Long eventId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Event event = eventRepository.findById(eventId).
                orElseThrow(() -> new NotFoundException("Событие не найдено"));
        List<Event> eventList = eventRepository.findByInitiatorId(user.getId());
        List<Request> requestList = requestRepository.findByEventIn(eventList);
        return requestList.stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult patchEventRequest(Long userId,
                                                     Long eventId,
                                                     EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Event event = eventRepository.findByIdAndInitiatorIdOrderByEventDateDesc(eventId,user.getId()).
                orElseThrow(() -> new NotFoundException("Событие не найдено"));
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> canceledRequests = new ArrayList<>();
        if (event.getParticipants().size()>event.getParticipantLimit()) {
            throw new ConflictException("Запросов больше, чем лимит заявок на событие");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Событие не подтверждено");
        }
        List<Request> requestList = requestRepository.findAllById(eventRequestStatusUpdateRequest.getRequestIds());
        requestList.forEach(r-> {
            if (eventRequestStatusUpdateRequest.getStatus() == Status.REJECTED) {
                r.setStatus(Status.REJECTED);
                canceledRequests.add(RequestMapper.toParticipationRequestDto(r));
            }
            if (eventRequestStatusUpdateRequest.getStatus()==Status.CONFIRMED) {
                r.setStatus(Status.CONFIRMED);
                confirmedRequests.add(RequestMapper.toParticipationRequestDto(r));
            }
        });
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(canceledRequests)
                .build();
    }



}

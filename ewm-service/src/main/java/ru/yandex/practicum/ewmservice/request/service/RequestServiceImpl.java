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
import ru.yandex.practicum.ewmservice.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.ewmservice.request.mapper.RequestMapper;
import ru.yandex.practicum.ewmservice.request.model.Request;
import ru.yandex.practicum.ewmservice.request.model.Status;
import ru.yandex.practicum.ewmservice.user.dao.UserRepository;
import ru.yandex.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

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
            request.setStatus(Status.CONFIRMED);
        } else {
            request.setStatus(Status.PENDING);
        }
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }


}

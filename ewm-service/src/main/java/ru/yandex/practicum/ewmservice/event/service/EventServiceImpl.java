package ru.yandex.practicum.ewmservice.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewmservice.category.dao.CategoryRepository;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.event.dao.EventRepository;
import ru.yandex.practicum.ewmservice.event.dto.*;
import ru.yandex.practicum.ewmservice.event.mapper.EventMapper;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.event.model.State;
import ru.yandex.practicum.ewmservice.event.model.StateAction;
import ru.yandex.practicum.ewmservice.exception.ConflictException;
import ru.yandex.practicum.ewmservice.exception.NotFoundException;
import ru.yandex.practicum.ewmservice.location.mapper.LocationMapper;
import ru.yandex.practicum.ewmservice.location.model.Location;
import ru.yandex.practicum.ewmservice.location.service.LocationService;
import ru.yandex.practicum.ewmservice.user.dao.UserRepository;
import ru.yandex.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.yandex.practicum.ewmservice.category.mapper.CategoryMapper.toCategoryDto;
import static ru.yandex.practicum.ewmservice.location.mapper.LocationMapper.toLocationDto;
import static ru.yandex.practicum.ewmservice.user.mapper.UserMapper.toUserShortDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationService locationService;

    @Override
    public EventFullDto postEvent(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Category category = categoryRepository.findById(newEventDto.getCategory()).
                orElseThrow(() -> new NotFoundException("Категория не найдена"));
        Location location = LocationMapper.fromLocationDto(locationService.createLocation(newEventDto.getLocation()));
        Event event = eventRepository.
                save(EventMapper.fromNewEventDto(newEventDto, category, location, user));
        EventFullDto returnEvent = EventMapper.toEventFullDto(event,
                toCategoryDto(category),
                toUserShortDto(user),
                toLocationDto(location)
        );
        return returnEvent;
    }

    @Override
    public List<EventShortDto> getAllEvents(Long userId, Integer from, Integer size) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Pageable pageable = PageRequest.of(from, size);
        List<EventShortDto> eventList = eventRepository.findByInitiatorIdOrderByEventDateDesc(user.getId(), pageable)
                .stream()
                .map(event -> EventMapper.eventShortDto(event,
                        toUserShortDto(user),
                        toCategoryDto(event.getCategory()),
                        toLocationDto(event.getLocation())))
                .collect(Collectors.toList());
        return eventList;
    }

    @Override
    public EventFullDto getEventById(Long userId, Long eventId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Event event = eventRepository.findByIdAndInitiatorIdOrderByEventDateDesc(eventId, userId).
                orElseThrow(() -> new NotFoundException("Событие не найдено"));
        return EventMapper.toEventFullDto(event,
                toCategoryDto(event.getCategory()),
                toUserShortDto(user),
                toLocationDto(event.getLocation())
        );
    }

    @Override
    public EventFullDto patchEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Event event = eventRepository.findByIdAndInitiatorIdOrderByEventDateDesc(eventId, userId).
                orElseThrow(() -> new NotFoundException("Событие не найдено"));
        if (!event.getInitiator().getId().equals(user.getId())) {
            throw new ConflictException("Пользовательне является инициатором этого события");
        }
        if (!(event.getState().equals(State.CANCELED) || event.getState().equals(State.PENDING))) {
            throw new ConflictException("Событие должно быть в ожидании модерации или отмененное");
        }
        checkUpdateParams(event, updateEventUserRequest);
        StateAction stateAction = updateEventUserRequest.getStateAction();
        switch (stateAction) {
            case CANCEL_REVIEW:
                event.setState(State.CANCELED);
                break;
            case SEND_TO_REVIEW:
                event.setState(State.PENDING);
                break;
        }
        Event eventToReturn = eventRepository.save(event);
        return EventMapper.toEventFullDto(eventToReturn,
                toCategoryDto(eventToReturn.getCategory()),
                toUserShortDto(user),
                toLocationDto(eventToReturn.getLocation())
        );
    }

    @Override
    public List<EventFullDto> getAdminEventList(List<Long> users,
                                                List<State> states,
                                                List<Long> categories,
                                                LocalDateTime rangeStart,
                                                LocalDateTime rangeEnd,
                                                Integer from,
                                                Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Event> eventList = eventRepository.findByInitiatorInAndStateInAndCategoryIn(users,
                states, categories, pageable);
        return eventList.stream()
                .map(event -> EventMapper.toEventFullDto(event,
                        toCategoryDto(event.getCategory()),
                        toUserShortDto(event.getInitiator()),
                        toLocationDto(event.getLocation())))
                .collect(Collectors.toList());
    }

    public EventFullDto updateEventAdmin(Long eventId, UpdateAdminRequest updateAdminRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Событие не найдено"));

        if (event.getState() == State.PUBLISHED) {
            throw new ConflictException("Только отменненые события или события в статусе рассмотрения можно изменить");
        }
        checkUpdateAdminParams(event, updateAdminRequest);

        if (!Objects.isNull(updateAdminRequest.getStateAction())) {
            StateAction state = updateAdminRequest.getStateAction();
            switch (state) {
                case PUBLISH_EVENT:
                    if (event.getState() != State.PENDING) {
                        throw new ConflictException("Только события в статус рассмотрения можно изменить");
                    }
                    event.setState(State.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                case REJECT_EVENT:
                    event.setState(State.CANCELED);
                    break;
                default:
                    throw new ConflictException("Только события в статусе рассмотрения можно изменить");
            }
        }

        Event updatedEvent = eventRepository.save(event);

        return EventMapper.toEventFullDto(updatedEvent,
                toCategoryDto(updatedEvent.getCategory()),
                toUserShortDto(updatedEvent.getInitiator()),
                toLocationDto(updatedEvent.getLocation()));
    }

    private void checkUpdateAdminParams(Event event, UpdateAdminRequest updateAdminRequest) {
        if (updateAdminRequest.getAnnotation() != null) {
            event.setAnnotation(updateAdminRequest.getAnnotation());
        }
        if (updateAdminRequest.getCategory() != null) {
            Category category = categoryRepository.findById(updateAdminRequest.getCategory()).orElseThrow(
                    () -> new NotFoundException("Событие не найдено"));
            event.setCategory(category);
        }
        if (updateAdminRequest.getDescription() != null) {
            event.setDescription(updateAdminRequest.getDescription());
        }
        if (updateAdminRequest.getEventDate() != null) {
            event.setEventDate(updateAdminRequest.getEventDate());
        }
        if (updateAdminRequest.getLocation() != null) {
            Location location = LocationMapper.fromLocationDto(locationService.createLocation(LocationMapper.toLocationDto(updateAdminRequest.getLocation())));
            event.setLocation(location);
        }
        if (updateAdminRequest.getPaid() != null) {
            event.setPaid(updateAdminRequest.getPaid());
        }
        if (updateAdminRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateAdminRequest.getParticipantLimit());
        }
        if (updateAdminRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateAdminRequest.getRequestModeration());
        }
        if (updateAdminRequest.getTitle() != null) {
            event.setTitle(updateAdminRequest.getTitle());
        }
    }

    private void checkUpdateParams(Event event, UpdateEventUserRequest updateEventUserRequest) {
        if (updateEventUserRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getCategory() != null) {
            event.setCategory(updateEventUserRequest.getCategory());
        }
        if (updateEventUserRequest.getDescription() != null) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
        if (updateEventUserRequest.getEventDate() != null) {
            event.setEventDate(updateEventUserRequest.getEventDate());
        }
        if (updateEventUserRequest.getLocation() != null) {
            event.setLocation(updateEventUserRequest.getLocation());
        }
        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
        if (updateEventUserRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
    }
}

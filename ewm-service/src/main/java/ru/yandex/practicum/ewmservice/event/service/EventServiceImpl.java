package ru.yandex.practicum.ewmservice.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewmservice.category.dao.CategoryRepository;
import ru.yandex.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.event.dao.EventRepository;
import ru.yandex.practicum.ewmservice.event.dto.EventFullDto;
import ru.yandex.practicum.ewmservice.event.dto.EventShortDto;
import ru.yandex.practicum.ewmservice.event.dto.NewEventDto;
import ru.yandex.practicum.ewmservice.event.dto.UpdateEventUserRequest;
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
import ru.yandex.practicum.ewmservice.user.mapper.UserMapper;
import ru.yandex.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
                CategoryMapper.toCategoryDto(category),
                UserMapper.toUserShortDto(user),
                LocationMapper.toLocationDto(location)
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
                        UserMapper.toUserShortDto(user),
                        CategoryMapper.toCategoryDto(event.getCategory()),
                        LocationMapper.toLocationDto(event.getLocation())))
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
                CategoryMapper.toCategoryDto(event.getCategory()),
                UserMapper.toUserShortDto(user),
                LocationMapper.toLocationDto(event.getLocation())
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
                CategoryMapper.toCategoryDto(eventToReturn.getCategory()),
                UserMapper.toUserShortDto(user),
                LocationMapper.toLocationDto(eventToReturn.getLocation())
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
                        CategoryMapper.toCategoryDto(event.getCategory()),
                        UserMapper.toUserShortDto(event.getInitiator()),
                        LocationMapper.toLocationDto(event.getLocation())))
                .collect(Collectors.toList());
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

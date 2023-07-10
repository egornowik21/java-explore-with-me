package ru.yandex.practicum.ewmservice.event.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.event.dto.EventFullDto;
import ru.yandex.practicum.ewmservice.event.dto.EventShortDto;
import ru.yandex.practicum.ewmservice.event.dto.NewEventDto;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.event.model.State;
import ru.yandex.practicum.ewmservice.location.dto.LocationDto;
import ru.yandex.practicum.ewmservice.location.model.Location;
import ru.yandex.practicum.ewmservice.user.dto.UserShortDto;
import ru.yandex.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static EventFullDto toEventFullDto(Event event, CategoryDto categoryDto, UserShortDto userShortDto, LocationDto locationDto) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryDto)
                .confirmedRequests(null)
                .createdOn(LocalDateTime.now())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(userShortDto)
                .location(locationDto)
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(null)
                .build();
    }

    public static EventShortDto eventShortDto(Event event, UserShortDto userShortDto, CategoryDto categoryDto, LocationDto locationDto) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryDto)
                .confirmedRequests(null)
                .eventDate(event.getEventDate())
                .initiator(userShortDto)
                .description(event.getDescription())
                .location(locationDto)
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .title(event.getTitle())
                .eventDate(event.getEventDate())
                .build();
    }

    public static NewEventDto newEventDto(Event event, CategoryDto categoryDto, LocationDto locationDto) {
        return NewEventDto.builder()
                .annotation(event.getAnnotation())
                .category(categoryDto.getId())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .location(locationDto)
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .title(event.getTitle())
                .build();
    }

    public static Event fromNewEventDto(NewEventDto newEventDto, Category category, Location location, User user) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .title(newEventDto.getTitle())
                .category(category)
                .description(newEventDto.getDescription())
                .paid(newEventDto.getPaid())
                .eventDate(newEventDto.getEventDate())
                .initiator(user)
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .location(location)
                .state(State.PENDING)
                .build();
    }


}

package ru.yandex.practicum.ewmservice.event.model;

import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.location.model.Location;
import ru.yandex.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;

public class Event {
    Long id;
    String annotation;
    String title;
    Category category;
    String description;
    Boolean paid;
    LocalDateTime eventDate;
    User initiator;
    Integer participantLimit;
    Boolean requestModeration;
    LocalDateTime publishedOn;
    Location location;
}

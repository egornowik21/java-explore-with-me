package ru.yandex.practicum.ewmservice.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.location.model.Location;
import ru.yandex.practicum.ewmservice.user.dto.UserDto;
import ru.yandex.practicum.ewmservice.user.dto.UserShortDto;
import ru.yandex.practicum.ewmservice.user.model.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    Long id;
    String annotation;
    String title;
    Category category;
    String description;
    Boolean paid;
    LocalDateTime eventDate;
    UserDto initiator;
    Integer participantLimit;
    Boolean requestModeration;
    LocalDateTime publishedOn;
    Location location;
}

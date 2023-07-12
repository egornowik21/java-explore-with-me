package ru.yandex.practicum.ewmservice.request.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.event.model.State;
import ru.yandex.practicum.ewmservice.request.model.Status;
import ru.yandex.practicum.ewmservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class ParticipationRequestDto {
    Long id;
    Long event;
    Long requester;
    Status status;
    LocalDateTime created;
}

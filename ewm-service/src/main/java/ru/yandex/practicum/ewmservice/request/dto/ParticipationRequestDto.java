package ru.yandex.practicum.ewmservice.request.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.request.model.Status;

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

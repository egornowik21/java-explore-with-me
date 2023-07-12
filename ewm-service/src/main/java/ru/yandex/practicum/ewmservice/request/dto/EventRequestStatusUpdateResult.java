package ru.yandex.practicum.ewmservice.request.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.request.model.Status;

import java.util.List;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class EventRequestStatusUpdateResult {
    List<Long> requestIds;
    Status status;
}

package ru.yandex.practicum.ewmservice.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PUBLIC)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchAdminEventParams {
    List<Long> users;
    List<State> states;
    List<Long> categories;
    String rangeStart;
    String rangeEnd;
    Integer from;
    Integer size;
}

package ru.yandex.practicum.ewmservice.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@FieldDefaults(level = AccessLevel.PUBLIC)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPublicEventParams {
    String text;
    List<Long> categories;
    Boolean paid;
    String rangeStart;
    String rangeEnd;
    Boolean onlyAvailable;
    EventSortType sort;
    Integer from;
    Integer size;
    HttpServletRequest request;
}

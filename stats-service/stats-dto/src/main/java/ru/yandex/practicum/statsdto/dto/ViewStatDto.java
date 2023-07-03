package ru.yandex.practicum.statsdto.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewStatDto {
    String app;
    String uri;
    Long hits;
}

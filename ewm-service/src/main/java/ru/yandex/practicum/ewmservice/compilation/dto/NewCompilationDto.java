package ru.yandex.practicum.ewmservice.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    List<Long> events;
    Boolean pinned;
    String title;
}

package ru.yandex.practicum.ewmservice.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.event.dto.EventShortDto;

import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    Long id;
    Set<EventShortDto> events = new HashSet<>();
    Boolean pinned;
    String title;
}

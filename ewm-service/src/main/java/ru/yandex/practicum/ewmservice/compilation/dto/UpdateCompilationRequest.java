package ru.yandex.practicum.ewmservice.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompilationRequest {
    List<Long> events;
    Boolean pinned;
    @Size(max = 50, min = 1)
    @NotBlank
    String title;
}

package ru.yandex.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.event.model.StateAction;
import ru.yandex.practicum.ewmservice.location.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequest {
    String annotation;
    Category category;
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NotNull(message = "Локация не может быть пустой")
    Location location;
    @NotNull(message = "Статус оплаты события не может быть пустым")
    Boolean paid;
    @NotNull(message = "Ограничение на количество участников не может быть пустым")
    Integer participantLimit;
    @NotNull(message = "Статус модерации не может быть пустым")
    Boolean requestModeration;
    @NotNull(message = "Изменение сотояния события не может быть пустым")
    StateAction stateAction;
    @NotBlank(message = "Заголовок не может быть пустым")
    String title;

}

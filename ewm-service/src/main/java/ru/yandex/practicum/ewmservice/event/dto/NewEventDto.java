package ru.yandex.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.location.dto.LocationDto;
import ru.yandex.practicum.ewmservice.location.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    @NotBlank
    @Size(max = 2000, min = 20)
    String annotation;
    @NotNull
    Long category;
    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 7000, min = 20)
    String description;
    @NotNull(message = "Дата события не может быть пустой")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NotNull(message = "Локация не может быть пустой")
    LocationDto location;
    @NotNull(message = "Информация по оплате не может быть пустой")
    Boolean paid;
    @NotNull(message = "Лимит участников не может быть пустым")
    Integer participantLimit;
    @NotNull
    Boolean requestModeration;
    @NotBlank(message = "Заголовок события заявки не может быть пустым")
    @Size(max = 120, min = 3)
    String title;
}

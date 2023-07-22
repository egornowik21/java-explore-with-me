package ru.yandex.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.event.model.State;
import ru.yandex.practicum.ewmservice.location.dto.LocationDto;
import ru.yandex.practicum.ewmservice.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    Long id;
    @NotBlank(message = "Аннотация не может быть пустой")
    String annotation;
    @NotNull(message = "Категория не может быть пустой")
    CategoryDto category;
    @NotNull(message = "Количество одобренных заявок не может быть пустым")
    Integer confirmedRequests;
    @NotNull(message = "Количество одобренных заявок не может быть пустым")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;
    @NotBlank(message = "Описание не может быть пустым")
    String description;
    @NotNull(message = "Дата события не может быть пустой")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NotNull(message = "Пользователь не может быть пустым")
    UserShortDto initiator;
    @NotNull(message = "Локация не может быть пустой")
    LocationDto location;
    @NotNull(message = "Статус оплаты события не может быть пустым")
    Boolean paid;
    @NotNull(message = "Ограничение на количество участников не может быть пустым")
    Integer participantLimit;
    @NotNull(message = "Дата публикации не может быть пустой")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;
    @NotNull(message = "Статус модерации не может быть пустым")
    Boolean requestModeration;
    @NotNull(message = "Статус события не может быть пустым")
    State state;
    @NotBlank(message = "Заголовок не может быть пустым")
    String title;
    @NotNull(message = "Количество просмотров не может быть пустым")
    Long views;
}

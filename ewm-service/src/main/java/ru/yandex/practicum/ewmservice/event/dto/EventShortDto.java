package ru.yandex.practicum.ewmservice.event.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.location.dto.LocationDto;
import ru.yandex.practicum.ewmservice.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class EventShortDto {
    Long id;
    @NotBlank(message = "Аннотация не может быть пустой")
    @Size(max = 2000, min = 20)
    String annotation;
    @NotNull(message = "Категория не может быть пустой")
    CategoryDto category;
    @NotNull(message = "Количество одобренных заявок не может быть пустым")
    Integer confirmedRequests;
    @NotNull(message = "Дата события не может быть пустой")
    LocalDateTime eventDate;
    @NotNull(message = "Пользователь не может быть пустым")
    UserShortDto initiator;
    @NotBlank(message = "Описание не может быть пустым")
    String description;
    @NotNull(message = "Локация не может быть пустой")
    LocationDto location;
    @NotNull(message = "Статус оплаты события не может быть пустым")
    Boolean paid;
    @NotNull(message = "Ограничение на количество участников не может быть пустым")
    Integer participantLimit;
    @NotBlank(message = "Заголовок не может быть пустым")
    String title;
    @NotNull(message = "Количество просмотров не может быть пустым")
    Integer views;
}

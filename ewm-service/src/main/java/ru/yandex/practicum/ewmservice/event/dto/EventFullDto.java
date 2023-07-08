package ru.yandex.practicum.ewmservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.event.model.State;
import ru.yandex.practicum.ewmservice.location.dto.LocationDto;
import ru.yandex.practicum.ewmservice.location.model.Location;
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
    @NotBlank
    String annotation;
    @NotNull
    CategoryDto category;
    @NotNull
    Integer confirmedRequests;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;
    @NotBlank
    String description;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NotNull
    UserShortDto initiator;
    @NotNull
    LocationDto location;
    @NotNull
    Boolean paid;
    @NotNull
    Integer participantLimit;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;
    @NotNull
    Boolean requestModeration;
    @NotNull
    State state;
    @NotBlank
    String title;
    @NotNull
    Integer views;
}

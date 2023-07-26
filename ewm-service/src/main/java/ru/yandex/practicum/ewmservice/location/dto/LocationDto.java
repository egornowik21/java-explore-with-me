package ru.yandex.practicum.ewmservice.location.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class LocationDto {
    Long id;
    Float lat;
    Float lon;
}

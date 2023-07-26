package ru.yandex.practicum.ewmservice.location.service;

import ru.yandex.practicum.ewmservice.location.dto.LocationDto;

public interface LocationService {
    LocationDto createLocation(LocationDto locationDto);
}

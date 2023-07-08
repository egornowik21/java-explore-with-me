package ru.yandex.practicum.ewmservice.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewmservice.location.dao.LocationRepository;
import ru.yandex.practicum.ewmservice.location.dto.LocationDto;
import ru.yandex.practicum.ewmservice.location.mapper.LocationMapper;
import ru.yandex.practicum.ewmservice.location.model.Location;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationDto createLocation(LocationDto locationDto) {
        Location location = locationRepository.save(LocationMapper.fromLocationDto(locationDto));
        return LocationMapper.toLocationDto(location);
    }

}

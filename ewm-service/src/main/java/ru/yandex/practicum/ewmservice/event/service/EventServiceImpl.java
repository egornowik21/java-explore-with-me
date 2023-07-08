package ru.yandex.practicum.ewmservice.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewmservice.category.dao.CategoryRepository;
import ru.yandex.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.event.dao.EventRepository;
import ru.yandex.practicum.ewmservice.event.dto.EventFullDto;
import ru.yandex.practicum.ewmservice.event.dto.NewEventDto;
import ru.yandex.practicum.ewmservice.event.mapper.EventMapper;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.exception.NotFoundException;
import ru.yandex.practicum.ewmservice.location.mapper.LocationMapper;
import ru.yandex.practicum.ewmservice.location.model.Location;
import ru.yandex.practicum.ewmservice.location.service.LocationService;
import ru.yandex.practicum.ewmservice.user.dao.UserRepository;
import ru.yandex.practicum.ewmservice.user.mapper.UserMapper;
import ru.yandex.practicum.ewmservice.user.model.User;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationService locationService;

    @Override
    public EventFullDto postEvent(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Category category = categoryRepository.findById(newEventDto.getCategory()).
                orElseThrow(() -> new NotFoundException("Категория не найдена"));
        Location location = LocationMapper.fromLocationDto(locationService.createLocation(newEventDto.getLocation()));
        Event event = eventRepository.
                save(EventMapper.fromNewEventDto(newEventDto,category,location,user));
        EventFullDto returnEvent = EventMapper.toEventFullDto(event,
                CategoryMapper.toCategoryDto(category),
                UserMapper.toUserShortDto(user),
                LocationMapper.toLocationDto(location)
                );
        return returnEvent;
    }
}

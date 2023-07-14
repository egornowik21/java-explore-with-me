package ru.yandex.practicum.ewmservice.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.yandex.practicum.ewmservice.compilation.dao.CompilationRepository;
import ru.yandex.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.yandex.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.ewmservice.compilation.mapper.CompilationMapper;
import ru.yandex.practicum.ewmservice.compilation.model.Compilation;
import ru.yandex.practicum.ewmservice.event.dao.EventRepository;
import ru.yandex.practicum.ewmservice.event.dto.EventShortDto;
import ru.yandex.practicum.ewmservice.event.mapper.EventMapper;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.location.mapper.LocationMapper;
import ru.yandex.practicum.ewmservice.user.mapper.UserMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto postCompilation(NewCompilationDto newCompilationDto) {
        List<Event> eventList = eventRepository.findAllById(newCompilationDto.getEvents());
        Set<EventShortDto> eventsSet = eventRepository.findAllById(newCompilationDto.getEvents()).stream()
                .map(event -> EventMapper.eventShortDto(event,
                        UserMapper.toUserShortDto(event.getInitiator()),
                        CategoryMapper.toCategoryDto(event.getCategory()),
                        LocationMapper.toLocationDto(event.getLocation())))
                .collect(Collectors.toSet());
        CompilationDto newCompilationToReturn = CompilationDto.builder()
                .events(eventsSet)
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .build();
        Compilation compilation = compilationRepository.save(CompilationMapper.toCompilation(newCompilationToReturn, eventList.stream().collect(Collectors.toSet())));
        return CompilationMapper.toCompilationDto(compilation, eventsSet);
    }
}

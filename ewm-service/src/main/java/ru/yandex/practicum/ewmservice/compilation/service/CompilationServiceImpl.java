package ru.yandex.practicum.ewmservice.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.ewmservice.category.mapper.CategoryMapper;
import ru.yandex.practicum.ewmservice.compilation.dao.CompilationRepository;
import ru.yandex.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.yandex.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.ewmservice.compilation.dto.UpdateCompilationRequest;
import ru.yandex.practicum.ewmservice.compilation.mapper.CompilationMapper;
import ru.yandex.practicum.ewmservice.compilation.model.Compilation;
import ru.yandex.practicum.ewmservice.event.dao.EventRepository;
import ru.yandex.practicum.ewmservice.event.dto.EventShortDto;
import ru.yandex.practicum.ewmservice.event.mapper.EventMapper;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.exception.NotFoundException;
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

    @Override
    public void deleteCompilationById(Long id) {
        Compilation compilation = compilationRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Подборка не найдена"));
        compilationRepository.deleteById(compilation.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilationById(Long comId) {
        Compilation compilation = compilationRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException("Подборка не найдена"));
        return CompilationMapper.toCompilationDto(compilation,
                compilation.getEvents().stream()
                        .map(event -> EventMapper.eventShortDto(event,
                                UserMapper.toUserShortDto(event.getInitiator()),
                                CategoryMapper.toCategoryDto(event.getCategory()),
                                LocationMapper.toLocationDto(event.getLocation())))
                        .collect(Collectors.toSet()));
    }

    @Override
    public List<CompilationDto> getCompilationDtoList(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Compilation> compilationList = compilationRepository.findByPinned(pinned, pageable);
        return compilationList.stream().
                map(c -> CompilationMapper.toCompilationDto(c, c.getEvents().stream().map(
                                        event -> EventMapper.eventShortDto(event,
                                                UserMapper.toUserShortDto(event.getInitiator()),
                                                CategoryMapper.toCategoryDto(event.getCategory()),
                                                LocationMapper.toLocationDto(event.getLocation()))
                                )
                                .collect(Collectors.toSet()))
                ).collect(Collectors.toList());
    }

    public CompilationDto patchCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка не найдена"));
        if (updateCompilationRequest.getEvents() != null) {
            List<Event> eventList = eventRepository.findAllById(updateCompilationRequest.getEvents());
            compilation.setEvents(eventList.stream().collect(Collectors.toSet()));
        }
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        Compilation compilationToReturn = compilationRepository.save(compilation);
        return CompilationMapper.toCompilationDto(compilationToReturn,
                compilation.getEvents().stream()
                        .map(event -> EventMapper.eventShortDto(event,
                                UserMapper.toUserShortDto(event.getInitiator()),
                                CategoryMapper.toCategoryDto(event.getCategory()),
                                LocationMapper.toLocationDto(event.getLocation())))
                        .collect(Collectors.toSet()));
    }


}

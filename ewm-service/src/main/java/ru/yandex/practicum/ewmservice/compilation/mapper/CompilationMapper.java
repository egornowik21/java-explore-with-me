package ru.yandex.practicum.ewmservice.compilation.mapper;

import ru.yandex.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.yandex.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.ewmservice.compilation.model.Compilation;
import ru.yandex.practicum.ewmservice.event.dto.EventShortDto;
import ru.yandex.practicum.ewmservice.event.model.Event;

import java.util.Set;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation,
                                                  Set<EventShortDto> eventShortDtoSet) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(eventShortDtoSet)
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static Compilation toCompilation(CompilationDto compilationDto,
                                            Set<Event> eventSet) {
        return Compilation.builder()
                .id(compilationDto.getId())
                .events(eventSet)
                .pinned(compilationDto.getPinned())
                .title(compilationDto.getTitle())
                .build();
    }

    public static Compilation toCompilationFromNew(NewCompilationDto newCompilationDto) {
        return Compilation.builder()
                .events(null)
                .pinned(null)
                .title(newCompilationDto.getTitle())
                .build();
    }
}

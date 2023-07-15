package ru.yandex.practicum.ewmservice.compilation.service;

import ru.yandex.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.yandex.practicum.ewmservice.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto postCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilationById(Long id);

    CompilationDto getCompilationById(Long comId);
    List<CompilationDto> getCompilationDtoList(Boolean pinned, Integer from, Integer size);
}

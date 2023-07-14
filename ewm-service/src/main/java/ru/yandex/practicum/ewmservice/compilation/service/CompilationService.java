package ru.yandex.practicum.ewmservice.compilation.service;

import ru.yandex.practicum.ewmservice.compilation.dao.CompilationRepository;
import ru.yandex.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.yandex.practicum.ewmservice.compilation.dto.NewCompilationDto;

public interface CompilationService {
    CompilationDto postCompilation(NewCompilationDto newCompilationDto);
}

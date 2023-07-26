package ru.yandex.practicum.ewmservice.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.yandex.practicum.ewmservice.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.ewmservice.compilation.dto.UpdateCompilationRequest;
import ru.yandex.practicum.ewmservice.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto saveNewCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("POST/compilations - админ добавил подборку");
        return compilationService.postCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("DELETE/compilations - удалена текущая подборка.");
        compilationService.deleteCompilationById(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto patchCompilation(@PathVariable Long compId,
                                           @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("PATCH/compilations - обновлена текущая подборка");
        return compilationService.patchCompilation(compId, updateCompilationRequest);
    }

}

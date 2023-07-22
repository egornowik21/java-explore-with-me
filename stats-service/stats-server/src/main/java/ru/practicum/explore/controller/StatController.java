package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.service.StatService;
import ru.yandex.practicum.statsdto.dto.EndpointHitDto;
import ru.yandex.practicum.statsdto.dto.ViewStatDto;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class StatController {

    private final StatService statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto addHit(@RequestBody EndpointHitDto hitDto) {
        log.info("POST/hits - отправлен запрос пользователем");
        return statService.addHit(hitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatDto> getStats(@RequestParam(required = false) String start,
                                      @RequestParam(required = false) String end,
                                      @RequestParam(required = false, defaultValue = "") List<String> uris,
                                      @RequestParam(required = false, defaultValue = "False") Boolean unique) {
        log.info("GET/stats - получена статистика по посещениям.");
        return statService.getStats(start, end, uris, unique);
    }

}

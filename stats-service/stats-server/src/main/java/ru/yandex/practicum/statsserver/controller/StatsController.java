package ru.yandex.practicum.statsserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.statsserver.dto.EndpointHitDto;
import ru.yandex.practicum.statsserver.dto.ViewStatDto;
import ru.yandex.practicum.statsserver.service.StatService;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatService statService;

    @GetMapping("/stats")
    public List<ViewStatDto> getStats(@RequestParam(name = "start") String start,
                                      @RequestParam(name = "end") String end,
                                      @RequestParam(name = "uris", defaultValue = "") List<String> uris,
                                      @RequestParam(name = "unique",defaultValue = "False") Boolean unique) {
        log.info("GET/stats - получена статистика по посещениям.");
        return statService.getListStats(start, end, uris,unique);
    }

    @PostMapping("/hit")
    public EndpointHitDto postHit(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("POST/hits - отправлен запрос пользователем");
        return statService.createHitDto(endpointHitDto);
    }
}

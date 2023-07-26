package ru.practicum.explore.service;

import ru.yandex.practicum.statsdto.dto.EndpointHitDto;
import ru.yandex.practicum.statsdto.dto.ViewStatDto;

import java.util.List;

public interface StatService {

    EndpointHitDto addHit(EndpointHitDto hitDto);

    List<ViewStatDto> getStats(String start, String end, List<String> uris, Boolean unique);

}

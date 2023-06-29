package ru.yandex.practicum.statsserver.service;

import ru.yandex.practicum.statsserver.dto.EndpointHitDto;
import ru.yandex.practicum.statsserver.dto.ViewStatDto;

import java.util.List;

public interface StatService {
    EndpointHitDto createHitDto(EndpointHitDto endpointHitDto);
    List<ViewStatDto> getListStats(String start, String end,List<String> uris,Boolean unique);
}

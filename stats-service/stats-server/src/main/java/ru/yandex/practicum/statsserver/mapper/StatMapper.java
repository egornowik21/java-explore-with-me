package ru.yandex.practicum.statsserver.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.statsserver.dto.EndpointHitDto;
import ru.yandex.practicum.statsserver.dto.ViewStatDto;
import ru.yandex.practicum.statsserver.model.Stat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static EndpointHitDto toEndpointHitDto(Stat stat) {
        return EndpointHitDto.builder()
                .id(stat.getId())
                .app(stat.getApp())
                .uri(stat.getUri())
                .ip(stat.getIp())
                .timestamp(stat.getTimestamp().format(FORMATTER))
                .build();
    }

    public static Stat inEndpointHitDto(EndpointHitDto endpointHitDto) {
        return Stat.builder()
                .id(endpointHitDto.getId())
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .ip(endpointHitDto.getIp())
                .timestamp(LocalDateTime.parse(endpointHitDto.getTimestamp(),FORMATTER))
                .build();
    }

    public static ViewStatDto toViewStatDto(Stat stat) {
        return ViewStatDto.builder()
                .app(stat.getApp())
                .uri(stat.getUri())
                .hits(null)
                .build();
    }

}

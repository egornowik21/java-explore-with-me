package ru.practicum.explore.mapper;

import lombok.NoArgsConstructor;
import ru.practicum.explore.model.Hit;
import ru.practicum.explore.model.Stats;
import ru.yandex.practicum.statsdto.dto.EndpointHitDto;
import ru.yandex.practicum.statsdto.dto.ViewStatDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public class HitMapper {

    public static Hit toHit(EndpointHitDto hitDto) {
        return Hit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .datetime(parseDate(hitDto.getTimestamp()))
                .build();
    }

    public static EndpointHitDto toHitDto(Hit hit) {
        return EndpointHitDto.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getDatetime().toString())
                .build();
    }

    public static ViewStatDto toStatsDto(Stats stats) {
        return ViewStatDto.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .hits(stats.getHits())
                .build();
    }

    private static LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.from(formatter.parse(date.replace("T", " ").substring(0, 19)));
    }

}

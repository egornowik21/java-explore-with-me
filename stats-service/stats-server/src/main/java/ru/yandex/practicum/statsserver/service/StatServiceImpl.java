package ru.yandex.practicum.statsserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.statsserver.dao.StatRepostitory;
import ru.yandex.practicum.statsserver.dto.EndpointHitDto;
import ru.yandex.practicum.statsserver.dto.ViewStatDto;
import ru.yandex.practicum.statsserver.mapper.StatMapper;
import ru.yandex.practicum.statsserver.model.Stat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepostitory statRepostitory;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EndpointHitDto createHitDto(EndpointHitDto endpointHitDto) {
        Stat newStat = statRepostitory.save(StatMapper.inEndpointHitDto(endpointHitDto));
        return StatMapper.toEndpointHitDto(newStat);
    }

    public List<ViewStatDto> getListStats(String start, String end,List<String> uris, Boolean unique) {
        LocalDateTime startsTime = LocalDateTime.parse(start,FORMATTER);
        LocalDateTime endsTime = LocalDateTime.parse(end,FORMATTER);
        List<ViewStatDto> returnList;
        if (unique) {
            if (uris.isEmpty()) {
                returnList = statRepostitory.findByCreatedBetweenDistinctWithoutUris(startsTime,endsTime);
            }
            else {
                returnList = statRepostitory.findByCreatedBetweenDistinct(startsTime,endsTime,uris);
            }
        }
        else {
            if (uris.isEmpty()) {
                returnList = statRepostitory.findByCreatedBetweenWithoutUris(startsTime,endsTime);
            }
            else {
                returnList = statRepostitory.findByCreatedBetween(startsTime,endsTime,uris);
            }
        }
        return returnList;
    }


}

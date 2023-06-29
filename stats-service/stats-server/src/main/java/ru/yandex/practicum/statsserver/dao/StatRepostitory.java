package ru.yandex.practicum.statsserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.yandex.practicum.statsdto.dto.ViewStatDto;
import ru.yandex.practicum.statsserver.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepostitory extends JpaRepository<Stat, Long> {

    @Query(value = "select new ru.yandex.practicum.statsdto.dto.ViewStatDto(st.app, st.uri,count(st.uri) as hits) " +
            "from Stat as st " +
            "where timestamp between ?1 and ?2 " +
            "and st.uri in ?3 " +
            "group by st.app, st.uri " +
            "order by hits desc")
    List<ViewStatDto> findByCreatedBetween(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "select new ru.yandex.practicum.statsdto.dto.ViewStatDto(st.app, st.uri,count(DISTINCT st.uri) as hits) " +
            "from Stat as st " +
            "where timestamp between ?1 and ?2 " +
            "and st.uri in ?3 " +
            "group by st.app, st.uri " +
            "order by hits desc")
    List<ViewStatDto> findByCreatedBetweenDistinct(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "select new ru.yandex.practicum.statsdto.dto.ViewStatDto(st.app, st.uri,count(st.uri) as hits) " +
            "from Stat as st " +
            "where timestamp between ?1 and ?2 " +
            "group by st.app, st.uri " +
            "order by hits desc")
    List<ViewStatDto> findByCreatedBetweenWithoutUris(LocalDateTime start, LocalDateTime end);

    @Query(value = "select new ru.yandex.practicum.statsdto.dto.ViewStatDto(st.app, st.uri,count(DISTINCT st.uri) as hits) " +
            "from Stat as st " +
            "where timestamp between ?1 and ?2 " +
            "and st.uri in ?3 " +
            "group by st.app, st.uri " +
            "order by hits desc")
    List<ViewStatDto> findByCreatedBetweenDistinctWithoutUris(LocalDateTime start, LocalDateTime end);

}

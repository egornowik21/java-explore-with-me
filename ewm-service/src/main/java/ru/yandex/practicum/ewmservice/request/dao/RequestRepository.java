package ru.yandex.practicum.ewmservice.request.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.request.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Long> {

    Request findOneByEventIdAndRequesterId(Long eventId, Long userId);
    List<Request> findByRequesterId(Long userId);
    List<Request> findByEventIn(List<Event> eventList);
}

package ru.yandex.practicum.ewmservice.request.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findOneByEventIdAndRequesterId(Long eventId, Long userId);

    List<Request> findByRequesterId(Long userId);

    List<Request> findByEventIn(List<Event> eventList);
}

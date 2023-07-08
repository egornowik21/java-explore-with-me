package ru.yandex.practicum.ewmservice.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.ewmservice.event.model.Event;

public interface EventRepository extends JpaRepository<Event,Long> {

}

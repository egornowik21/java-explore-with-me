package ru.yandex.practicum.ewmservice.location.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.ewmservice.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}

package ru.yandex.practicum.ewmservice.event.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.event.model.State;
import ru.yandex.practicum.ewmservice.user.model.User;
import ru.yandex.practicum.statsdto.dto.ViewStatDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByInitiatorIdOrderByEventDateDesc (Long userId, Pageable pageable);
    Optional<Event> findByIdAndInitiatorIdOrderByEventDateDesc(Long id, Long userId);
    @Query("select e from Event e " +
            "where e.initiator.id in ?1 and " +
            "e.state in ?2 and e.category.id in ?3")
    List<Event> findByInitiatorInAndStateInAndCategoryIn(List<Long> users,
                                                           List<State> states,
                                                           List<Long> categoriesId,
                                                           Pageable pageable);

}

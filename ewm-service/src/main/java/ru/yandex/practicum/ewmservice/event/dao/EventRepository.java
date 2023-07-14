package ru.yandex.practicum.ewmservice.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.event.model.State;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByInitiatorIdOrderByEventDateDesc (Long userId, Pageable pageable);
    List<Event> findByInitiatorId (Long userId);
    Optional<Event> findByIdAndInitiatorIdOrderByEventDateDesc(Long id, Long userId);
    @Query("select e from Event e " +
            "where e.initiator.id in ?1 and " +
            "e.state in ?2 and e.category.id in ?3")
    List<Event> findByInitiatorInAndStateInAndCategoryIn(List<Long> users,
                                                           List<State> states,
                                                           List<Long> categoriesId,
                                                           Pageable pageable);

}

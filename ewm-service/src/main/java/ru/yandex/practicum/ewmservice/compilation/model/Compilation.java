package ru.yandex.practicum.ewmservice.compilation.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.event.model.Event;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@Entity
@Table(name = "compilations")
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "events_compilations",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    Set<Event> events = new HashSet<>();
    @Column(name = "pinned")
    Boolean pinned;
    @Column(name = "title")
    String title;
}

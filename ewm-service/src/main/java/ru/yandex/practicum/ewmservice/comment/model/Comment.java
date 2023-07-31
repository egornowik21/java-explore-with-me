package ru.yandex.practicum.ewmservice.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.ewmservice.event.model.Event;
import ru.yandex.practicum.ewmservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    User author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    Event event;
    LocalDateTime created;
}

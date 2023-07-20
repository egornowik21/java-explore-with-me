package ru.yandex.practicum.ewmservice.event.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.WhereJoinTable;
import ru.yandex.practicum.ewmservice.category.dto.CategoryDto;
import ru.yandex.practicum.ewmservice.category.model.Category;
import ru.yandex.practicum.ewmservice.location.model.Location;
import ru.yandex.practicum.ewmservice.user.dto.UserDto;
import ru.yandex.practicum.ewmservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "annotation", nullable = false)
    String annotation;
    @Column(name = "title", nullable = false)
    String title;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    Category category;
    @Column(name = "description", nullable = false)
    String description;
    @Column(name = "paid", nullable = false)
    Boolean paid;
    @Column(name = "event_date", nullable = false)
    LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    User initiator;
    @Column(name = "participant_limit", nullable = false)
    Integer participantLimit;
    @Column(name = "request_Moderation", nullable = false)
    Boolean requestModeration;
    @Column(name = "published_On", nullable = false)
    LocalDateTime publishedOn;
    @Column(name = "created_on", nullable = false)
    LocalDateTime createdOn;
    @ManyToOne
    @JoinColumn(name = "loc_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    Location location;
    @Enumerated(EnumType.STRING)
    State state;
    @WhereJoinTable(clause = "status='CONFIRMED'")
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "requests", joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> participants = new HashSet<>();
    @Column(name = "views", nullable = false)
    Long views;
}

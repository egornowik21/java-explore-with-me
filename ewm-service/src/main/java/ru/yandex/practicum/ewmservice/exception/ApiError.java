package ru.yandex.practicum.ewmservice.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ApiError {
    private String message;
    private String reason;
    private HttpStatus status;
    private List<String> errors;
    private LocalDateTime timestamp;
}

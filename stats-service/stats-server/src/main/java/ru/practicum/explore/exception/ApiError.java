package ru.practicum.explore.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiError {

    private final HttpStatus status;
    private final String reason;
    private final String message;
    private final List<String> errors;
    private final LocalDateTime timestamp;

    public ApiError(HttpStatus status, String reason, String message, List<String> errors, LocalDateTime timestamp) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;
    }

}

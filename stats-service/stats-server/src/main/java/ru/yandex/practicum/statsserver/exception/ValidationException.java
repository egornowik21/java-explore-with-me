package ru.yandex.practicum.statsserver.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String s) {
        super(s);
    }
}

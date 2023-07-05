package ru.yandex.practicum.ewmservice.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String s) {
        super(s);
    }
}

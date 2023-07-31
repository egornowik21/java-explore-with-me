package ru.yandex.practicum.ewmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.practicum.ewm", "ru.practicum.statsclient", "ru.yandex.practicum.ewmservice.user",
        "ru.yandex.practicum.ewmservice.category", "ru.yandex.practicum.ewmservice.compilation",
        "ru.yandex.practicum.ewmservice.event", "ru.yandex.practicum.ewmservice.exception", "ru.yandex.practicum.ewmservice.location",
        "ru.yandex.practicum.ewmservice.request","ru.yandex.practicum.statsclient","ru.yandex.practicum.ewmservice.comment"})
public class EwmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EwmServiceApplication.class, args);
    }

}

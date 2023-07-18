package ru.yandex.practicum.ewmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.practicum.ewmservice", "ru.practicum.statsclient"})
public class EwmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EwmServiceApplication.class, args);
    }

}

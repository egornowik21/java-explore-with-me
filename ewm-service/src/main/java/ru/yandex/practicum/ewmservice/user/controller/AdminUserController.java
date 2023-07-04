package ru.yandex.practicum.ewmservice.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewmservice.user.dto.UserDto;
import ru.yandex.practicum.ewmservice.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {
    private final UserService userService;

    @PostMapping
    public UserDto saveNewUser(@RequestBody UserDto userDto) {
        log.info("POST/users - добавлен текущий пользователь.");
        return userService.postUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("DELETE/users - удален текущий пользователь.");
        userService.deleteUserById(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(value = "ids",required = false) Long ids,
                                     @RequestParam(defaultValue = "0") Integer from,
                                     @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET/users - получен список всех пользователей.");
        return userService.getAllUsers(ids,from,size);
    }
}

package ru.yandex.practicum.ewmservice.user.service;

import ru.yandex.practicum.ewmservice.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers(Long userId, Integer from, Integer size);
    UserDto postUser(UserDto userDto);
    void deleteUserById(Long userId);
}

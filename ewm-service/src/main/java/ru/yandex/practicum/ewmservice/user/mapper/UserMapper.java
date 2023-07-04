package ru.yandex.practicum.ewmservice.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.ewmservice.user.dto.UserDto;
import ru.yandex.practicum.ewmservice.user.dto.UserShortDto;
import ru.yandex.practicum.ewmservice.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User inUserDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public static UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public static User inUserShortDto(UserShortDto userShortDto) {
        return User.builder()
                .id(userShortDto.getId())
                .name(userShortDto.getName())
                .email(null)
                .build();
    }
}

package ru.yandex.practicum.ewmservice.user.dto;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class UserDto {
    Long id;
    @NotBlank
    @NotNull
    String name;
    @NotBlank
    @Email
    String email;
}

package ru.yandex.practicum.ewmservice.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class UserDto {
    Long id;
    @NotBlank
    @Size(max = 250, min = 2)
    String name;
    @NotBlank
    @Email
    @Size(max = 254, min = 6)
    String email;
}

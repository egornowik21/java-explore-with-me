package ru.yandex.practicum.ewmservice.category.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class CategoryDto {
    Long id;
    @NotBlank(message = "имя категории не может быть пустым")
    @NotNull(message = "имя категории не может быть пустым")
    String name;
}

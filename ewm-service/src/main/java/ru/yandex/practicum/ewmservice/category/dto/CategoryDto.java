package ru.yandex.practicum.ewmservice.category.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class CategoryDto {
    Long id;
    @NotBlank(message = "имя категории не может быть пустым")
    @Size(max = 50)
    String name;
}

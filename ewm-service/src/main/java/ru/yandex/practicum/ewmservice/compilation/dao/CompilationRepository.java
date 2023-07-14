package ru.yandex.practicum.ewmservice.compilation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.ewmservice.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation,Long> {

}

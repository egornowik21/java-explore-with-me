package ru.yandex.practicum.ewmservice.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewmservice.exception.ConflictException;
import ru.yandex.practicum.ewmservice.user.dao.UserRepository;
import ru.yandex.practicum.ewmservice.user.dto.UserDto;
import ru.yandex.practicum.ewmservice.user.mapper.UserMapper;
import ru.yandex.practicum.ewmservice.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<User> userDtoList;
        if (userId == null) {
            userDtoList = repository.findAll(pageable);
        } else {
            userDtoList = repository.findAllById(userId, pageable);
        }
        return userDtoList
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto postUser(UserDto userDto) {
        if (repository.findByName(userDto.getName()).size()>0) {
            throw new ConflictException("Имя категории уже существует");
        }
        User newUser = repository.save(UserMapper.inUserDto(userDto));
        return UserMapper.toUserDto(newUser);
    }

    @Override
    public void deleteUserById(Long userId) {
        repository.deleteById(userId);
    }
}

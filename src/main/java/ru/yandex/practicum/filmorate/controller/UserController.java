package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    @GetMapping(value = "/users")
    public List<User> getAll() {
        log.info(String.format("Количество пользователей: %d", users.size()));
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User newUser) {
        checkName(newUser);
        newUser.setId(id++);
        log.info(String.format("Пользователь %s добален.", newUser.getLogin()));
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User newUser) {
        checkName(newUser);
        if (users.containsKey(newUser.getId())) {
            log.info(String.format("Пользователь %s обновлен.", newUser.getLogin()));
            users.put(newUser.getId(), newUser);
        } else {
            log.warn(String.format("Пользователь %s не найден.", newUser.getLogin()));
            throw new ValidationException("Такого пользователя не существует.");
        }
        return newUser;
    }

    private void checkName(User newUser) {
        if (newUser.getName() == null || newUser.getName().isEmpty()) {
            newUser.setName(newUser.getLogin());
        }
    }
}

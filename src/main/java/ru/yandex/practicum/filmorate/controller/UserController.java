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
        log.info("Количество пользователей: " + users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User newUser) {
        checkName(newUser);
        newUser.setId(id++);
        log.info("Пользователь " + newUser.getLogin() + " добален.");
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User newUser) {
        checkName(newUser);
        if (users.containsKey(newUser.getId())) {
            log.info("Пользователь " + newUser.getLogin() + " обновлен.");
            users.put(newUser.getId(), newUser);
        } else {
            log.warn("Пользователь " + newUser.getLogin() + " не найден.");
            throw new ValidationException("Такого пользователя не существует.");
        }
        return newUser;
    }

    private void checkName(User newUser) {
        if (newUser.getName() == null || newUser.getName().isBlank()) {
            newUser.setName(newUser.getLogin());
        }
    }
}

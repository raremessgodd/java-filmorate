package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;

@RestController("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();

    @GetMapping
    public HashMap<Integer, User> getAll() {
        return users;
    }

    @PostMapping
    public void create(@RequestBody User newUser) {
        if (checkValidation(newUser)) {
            log.info("Пользователь " + newUser.getLogin() + " добален.");
            users.put(newUser.getId(), newUser);
        }
    }

    @PutMapping
    public void update(@RequestBody User newUser) {
        if (checkValidation(newUser)) {
            log.info("Пользователь " + newUser.getLogin() + " обновлен.");
            users.put(newUser.getId(), newUser);
        }
    }

    private boolean checkValidation (User newUser) {
        if (newUser.getEmail().isEmpty() || !newUser.getEmail().contains("@")) {
            log.warn("Указан некорректный e-mail.", new ValidationException());
            throw new ValidationException("Указан некорректный e-mail.");
        }
        if (newUser.getLogin().isEmpty() || newUser.getLogin().contains(" ")) {
            log.warn("Указан некорректный логин.", new ValidationException());
            throw new ValidationException("Указан некорректный логин.");
        }
        if (newUser.getBirthdayDate().isAfter(LocalDate.now())) {
            log.warn("День рождения не может быть позже настоящего времени.", new ValidationException());
            throw new ValidationException("День рождения не может быть позже настоящего времени.");
        }
        if (newUser.getName().isEmpty()) {
            newUser.setName(newUser.getLogin());
        }
        return true;
    }
}

package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;

@RestController("/posts")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping
    public HashMap<Integer, Film> getAll() {
        return films;
    }

    @PostMapping
    public void create(@RequestBody Film newFilm) {
        if (checkValidation(newFilm)) {
            log.info("Фильм " + newFilm.getName() + " добавлен.");
            films.put(newFilm.getId(), newFilm);
        }
    }

    @PutMapping
    public void update(@RequestBody Film newFilm) {
        if (checkValidation(newFilm)) {
            log.info("Фильм " + newFilm.getName() + " обновлен.");
            films.put(newFilm.getId(), newFilm);
        }
    }

    private boolean checkValidation(Film newFilm) {
        if (newFilm.getName().isEmpty()) {
            log.warn("Не заполнено поле названия фильма.", new ValidationException());
            throw new ValidationException("Не заполнено поле названия фильма.");
        }
        if (newFilm.getDescription().length() > 200) {
            log.warn("Описание фильма слишком длинное.", new ValidationException());
            throw new ValidationException("Описание фильма слишком длинное.");
        }
        if (newFilm.getReleaseDate().isBefore(LocalDate.of(1895, 1, 28))) {
            log.warn("Указана неверная дата выпуска фильма.", new ValidationException());
            throw new ValidationException("Указана неверная дата выпуска фильма.");
        }
        if (newFilm.getDuration().isNegative()) {
            log.warn("Продолжительность фильма не может быть отрицательной.", new ValidationException());
            throw new ValidationException("Продолжительность фильма не может быть отрицательной.");
        }
        return true;
    }
}

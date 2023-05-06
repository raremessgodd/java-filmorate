package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping(value = "/films")
    public List<Film> getAll() {
        log.info("Количство загруженых фильмов: " + films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film newFilm) {
        newFilm.setId(id++);
        log.info("Фильм " + newFilm.getName() + " добавлен.");
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film newFilm) {
        if (films.containsKey(newFilm.getId())) {
            log.info("Фильм " + newFilm.getName() + " обновлен.");
            films.put(newFilm.getId(), newFilm);
        } else {
            log.info("Фильм " + newFilm.getName() + " не найден.");
            throw new ValidationException("Такого фильма не существует.");
        }
        return newFilm;
    }
}

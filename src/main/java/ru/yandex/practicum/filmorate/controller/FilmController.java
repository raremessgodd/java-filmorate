package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class FilmController {
    private final FilmService service = new FilmService(new InMemoryFilmStorage());

    @GetMapping(value = "/films")
    public List<Film> getAll() {
        return service.getAllFilms();
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film newFilm) {
        return service.addFilm(newFilm);
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film newFilm) {
        return service.updateFilm(newFilm);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        service.addLike(userId, id);
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        service.removeLike(userId, id);
    }

    @GetMapping(value = "/films/popular")
    public List<Film> getPopular(@RequestParam(required = false) Integer count) {
        return service.getMostPopularFilms(count);
    }

    @GetMapping(value = "/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        return service.getFilmById(id);
    }
}

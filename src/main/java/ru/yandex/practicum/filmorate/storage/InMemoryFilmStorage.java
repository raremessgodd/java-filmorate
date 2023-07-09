package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NoSuchFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;
    @Override
    public Film addFilm(Film film) {
        film.setId(id++);
        log.info(String.format("Фильм %s добавлен.", film.getName()));
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void deleteFilm(Film film) {
        if (films.containsKey(film.getId())) {
            log.info(String.format("Фильм %s удален.", film.getName()));
            films.remove(film.getId());
        } else {
            log.info(String.format("Фильм %s не найден.", film.getName()));
            throw new NoSuchFilmException(String.valueOf(film.getId()));
        }
    }

    @Override
    public Film updateFilm(Film newFilm) {
        if (films.containsKey(newFilm.getId())) {
            log.info(String.format("Фильм %s обновлен.", newFilm.getName()));
            films.put(newFilm.getId(), newFilm);
        } else {
            log.info(String.format("Фильм %s не найден.", newFilm.getName()));
            throw new NoSuchFilmException(String.valueOf(newFilm.getId()));
        }
        return newFilm;
    }

    @Override
    public List<Film> getAllFilms() {
        log.info(String.format("Количество загруженых фильмов: %d", films.size()));
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            log.info(String.format("Фильм под id - %s не найден.", id));
            throw new NoSuchFilmException(String.valueOf(id));
        }
    }
}

package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NoSuchUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    public final FilmStorage storage;

    public FilmService(FilmStorage storage) {
        this.storage = storage;
    }

    public List<Film> getAllFilms() {
        return storage.getAllFilms();
    }

    public Film addFilm(Film newFilm) {
        return storage.addFilm(newFilm);
    }

    public Film updateFilm(Film newFilm) {
        return storage.updateFilm(newFilm);
    }

    public Film getFilmById(int id) {
        return storage.getFilmById(id);
    }

    public void addLike (int userId, int filmId) {
        if (userId < 0) {
            throw new NoSuchUserException(String.valueOf(userId));
        }
        storage.getFilmById(filmId).getLikes().add(userId);
    }

    public void removeLike (int userId, int filmId) {
        if (userId < 0) {
            throw new NoSuchUserException(String.valueOf(userId));
        }
        storage.getFilmById(filmId).getLikes().remove(userId);
    }

    public List<Film> getMostPopularFilms (Integer count) {
        List<Film> popularFilms = storage.getAllFilms().stream()
                .sorted((o1, o2) -> o2.getLikes().size() - o1.getLikes().size())
                .collect(Collectors.toList());

        if (count == null) {
            if (popularFilms.size() < 10) {
                return popularFilms.subList(0, popularFilms.size());
            }
            return popularFilms.subList(0, 11);
        } else {
            if (count > popularFilms.size()) {
                throw new IndexOutOfBoundsException("count " + count);
            }
            return popularFilms.subList(0, count);
        }
    }
}

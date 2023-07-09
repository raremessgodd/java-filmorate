package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NoSuchUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    public final FilmStorage storage = new InMemoryFilmStorage();

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

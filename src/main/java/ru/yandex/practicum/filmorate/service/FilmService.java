package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    public final FilmStorage storage = new InMemoryFilmStorage();

    public void addLike (int userId, int filmId) {
        storage.getFilmById(filmId).getLikes().add(userId);
    }

    public void removeLike (int userId, int filmId) {
        storage.getFilmById(filmId).getLikes().remove(userId);
    }

    public List<Film> getMostPopularFilms () {
        List<Film> popularFilms = storage.getAllFilms().stream()
                .sorted((Comparator.comparingInt(o -> o.getLikes().size())))
                .collect(Collectors.toList());
        return popularFilms.subList(0, 11);
    }
}

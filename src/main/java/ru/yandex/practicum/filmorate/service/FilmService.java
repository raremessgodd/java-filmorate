package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage storage = new InMemoryFilmStorage();

    public void addLike (User user, Film film) {
        film.getLikes().add(user.getId());
    }

    public void removeLike (User user, Film film) {
        film.getLikes().remove(user.getId());
    }

    public List<Film> getMostPopularFilms (User user, Film film) {
        List<Film> popularFilms = storage.getAllFilms().stream()
                .sorted((Comparator.comparingInt(o -> o.getLikes().size())))
                .collect(Collectors.toList());
        return popularFilms.subList(0, 11);
    }
}

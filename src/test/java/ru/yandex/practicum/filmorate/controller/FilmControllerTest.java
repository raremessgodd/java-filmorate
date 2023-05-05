package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private FilmController controller;
    private Film film;

    @BeforeEach
    void updateController() {
        controller = new FilmController();

        film = Film.builder()
                .id(1)
                .releaseDate(LocalDate.now())
                .description("description")
                .name("Test")
                .duration(Duration.ofHours(2))
                .build();
    }

    @Test
    void nameValidation() {
        film.setName("");

        ValidationException exc = assertThrows(ValidationException.class,
                () -> controller.create(film),
                "Не выкидывается исключение.");
        assertEquals("Не заполнено поле названия фильма.",
                exc.getMessage(),
                "Выводится неправильное сообщение.");
    }

    @Test
    void descriptionValidation() {
        film.setDescription("a".repeat(201));

        ValidationException exc = assertThrows(ValidationException.class,
                () -> controller.create(film),
                "Не выкидывается исключение.");
        assertEquals("Описание фильма слишком длинное.",
                exc.getMessage(),
                "Выводится неправильное сообщение.");
    }

    @Test
    void releaseDateValidation() {
        film.setReleaseDate(LocalDate.of(1895, 1, 27));

        ValidationException exc = assertThrows(ValidationException.class,
                () -> controller.create(film),
                "Не выкидывается исключение.");
        assertEquals("Указана неверная дата выпуска фильма.",
                exc.getMessage(),
                "Выводится неправильное сообщение.");
    }

    @Test
    void durationValidation() {
        film.setDuration(Duration.ZERO.minusNanos(1));

        ValidationException exc = assertThrows(ValidationException.class,
                () -> controller.create(film),
                "Не выкидывается исключение.");
        assertEquals("Продолжительность фильма не может быть отрицательной.",
                exc.getMessage(),
                "Выводится неправильное сообщение.");
    }
}
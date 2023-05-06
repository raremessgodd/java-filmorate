package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private Film film;

    @BeforeEach
    void setUp() {
        film = Film.builder()
                .id(1)
                .releaseDate(LocalDate.now())
                .description("description")
                .name("Test")
                .duration(100)
                .build();
    }

    @AfterEach
    void checkValidation() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Валидация некорректна");
    }

    @Test
    void nameValidation() {
        film.setName("");
    }

    @Test
    void descriptionValidation() {
        film.setDescription("a".repeat(201));
    }

    @Test
    void releaseDateValidation() {
        film.setReleaseDate(LocalDate.of(1895, 1, 27));
    }

    @Test
    void durationValidation() {
        film.setDuration(-1);
    }

    @AfterAll
    static void updateEmptyName() {
        FilmController controller = new FilmController();
        Film film = Film.builder()
                .id(1)
                .releaseDate(LocalDate.now())
                .description("description")
                .name("")
                .duration(100)
                .build();

        ValidationException e = assertThrows(ValidationException.class,
                () -> controller.update(film),
                "Не выкидывается исключение.");
        assertEquals("Такого фильма не существует.",
                e.getMessage(),
                "Выводится неверное сообщение об ошибке.");
    }
}
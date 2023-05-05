package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private UserController controller;

    private User user;

    @BeforeEach
    void updateController() {
        controller = new UserController();

        user = User.builder()
                .id(1)
                .email("test@yandex.ru")
                .login("test-login")
                .name("Tem")
                .birthdayDate(LocalDate.of(2004, 6, 25))
                .build();
    }

    @Test
    void emptyEmailValidation() {
        user.setEmail("");

        ValidationException exc = assertThrows(ValidationException.class,
                () -> controller.create(user),
                "Не выкидывается исключение.");
        assertEquals("Указан некорректный e-mail.",
                exc.getMessage(),
                "Выводится неправильное сообщение.");
    }

    @Test
    void invalidEmailValidation() {
        user.setEmail("test#yandex.ru");

        ValidationException exc = assertThrows(ValidationException.class,
                () -> controller.create(user),
                "Не выкидывается исключение.");
        assertEquals("Указан некорректный e-mail.",
                exc.getMessage(),
                "Выводится неправильное сообщение.");
    }

    @Test
    void emptyLoginValidation() {
        user.setLogin("");

        ValidationException exc = assertThrows(ValidationException.class,
                () -> controller.create(user),
                "Не выкидывается исключение.");
        assertEquals("Указан некорректный логин.",
                exc.getMessage(),
                "Выводится неправильное сообщение.");
    }

    @Test
    void invalidLoginValidation() {
        user.setLogin("test login");

        ValidationException exc = assertThrows(ValidationException.class,
                () -> controller.create(user),
                "Не выкидывается исключение.");
        assertEquals("Указан некорректный логин.",
                exc.getMessage(),
                "Выводится неправильное сообщение.");
    }

    @Test
    void birthdayDateValidation() {
        user.setBirthdayDate(LocalDate.now().plusDays(1));

        ValidationException exc = assertThrows(ValidationException.class,
                () -> controller.create(user),
                "Не выкидывается исключение.");
        assertEquals("День рождения не может быть позже настоящего времени.",
                exc.getMessage(),
                "Выводится неправильное сообщение.");
    }

    @Test
    void emptyName() {
        user.setName("");
        controller.update(user);
        assertEquals(user.getLogin(),
                user.getName(),
                "Имя пользователя не меняется на логин.");
    }
}
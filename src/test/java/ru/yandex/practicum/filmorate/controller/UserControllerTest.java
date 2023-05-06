package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .email("test@yandex.ru")
                .login("test-login")
                .name("Tem")
                .birthday(LocalDate.of(2004, 6, 25))
                .build();
    }

    @AfterEach
    void checkValidation() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Валидация некорректна");
    }

    @Test
    void emptyEmailValidation() {
        user.setEmail(null);
    }

    @Test
    void invalidEmailValidation() {
        user.setEmail("test#yandex.ru");
    }

    @Test
    void emptyLoginValidation() {
        user.setLogin("");
    }

    @Test
    void invalidLoginValidation() {
        user.setLogin("test login");
    }

    @Test
    void birthdayDateValidation() {
        user.setBirthday(LocalDate.now().plusDays(1));
    }

    @AfterAll
     static void createEmptyName() {
        UserController controller = new UserController();
        User user = User.builder()
                .id(1)
                .email("test@yandex.ru")
                .login("test-login")
                .name("")
                .birthday(LocalDate.of(2004, 6, 25))
                .build();

        controller.create(user);
        assertEquals(user.getLogin(), user.getName(), "Логин не заменяет имя.");
    }

    @AfterAll
    static void updateUnknownUser() {
        UserController controller = new UserController();
        User user = User.builder()
                .id(1)
                .email("test@yandex.ru")
                .login("test-login")
                .name("Tem")
                .birthday(LocalDate.of(2004, 6, 25))
                .build();

        ValidationException e = assertThrows(ValidationException.class,
                () -> controller.update(user),
                "Не выкидывается исключение.");
        assertEquals("Такого пользователя не существует.",
                e.getMessage(),
                "Выводится неверное сообщение об ошибке.");
    }
}
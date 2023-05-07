package ru.yandex.practicum.filmorate.controller;

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
    private UserController controller;

    @BeforeEach
    void setUp() {
        controller = new UserController();
        user = User.builder()
                .id(1)
                .email("test@yandex.ru")
                .login("test-login")
                .name("Tem")
                .birthday(LocalDate.of(2004, 6, 25))
                .build();
    }

    @Test
    void emptyEmailValidation() {
        user.setEmail(null);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Валидация некорректна");
    }

    @Test
    void invalidEmailValidation() {
        user.setEmail("test#yandex.ru");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Валидация некорректна");
    }

    @Test
    void emptyLoginValidation() {
        user.setLogin("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Валидация некорректна");
    }

    @Test
    void invalidLoginValidation() {
        user.setLogin("test login");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Валидация некорректна");
    }

    @Test
    void birthdayDateValidation() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Валидация некорректна");
    }

    @Test
     void createEmptyName() {
        user.setName("");
        controller.create(user);
        assertEquals(user.getLogin(), user.getName(), "Логин не заменяет имя.");
    }

    @Test
    void updateUnknownUser() {
        ValidationException e = assertThrows(ValidationException.class,
                () -> controller.update(user),
                "Не выкидывается исключение.");
        assertEquals("Такого пользователя не существует.",
                e.getMessage(),
                "Выводится неверное сообщение об ошибке.");
    }
}
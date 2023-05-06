package ru.yandex.practicum.filmorate.annotation;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class LoginValidator implements ConstraintValidator<Login, String> {
    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return !(login.contains(" ") || login.isEmpty());
    }
}

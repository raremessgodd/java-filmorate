package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class LoginValidator implements ConstraintValidator<Login, String> {
    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return !(login.contains(" ") || login.isEmpty());
    }
}

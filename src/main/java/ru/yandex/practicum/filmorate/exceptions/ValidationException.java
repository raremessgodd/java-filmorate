package ru.yandex.practicum.filmorate.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException() {
        super();
    }
    public ValidationException(String message) {
        super(message);
    }
}

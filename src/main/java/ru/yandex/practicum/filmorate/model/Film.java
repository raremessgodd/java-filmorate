package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.AfterFirstFilm;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    int id;
    @NotEmpty(message = "Не заполнено поле названия фильма.")
    String name;
    @Size(max = 200, message = "Описание фильма слишком длинное.")
    String description;
    @AfterFirstFilm(message = "Указана неверная дата выпуска фильма.")
    LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть отрицательной.")
    int duration;
}

package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.AfterFirstFilm;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;

@Data
@Builder
public class Film {
    private int id;
    @NotEmpty(message = "Не заполнено поле названия фильма.")
    private String name;
    @Size(max = 200, message = "Описание фильма слишком длинное.")
    private String description;
    @AfterFirstFilm(message = "Указана неверная дата выпуска фильма.")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма не может быть отрицательной.")
    private int duration;
    private final HashSet<Integer> likes = new HashSet<>();
}

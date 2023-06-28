package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    private int id;
    @Email(message = "Указан некорректный e-mail") @NotEmpty(message = "Email не указан")
    private String email;
    @NotNull(message = "Не указан логин") @Login(message = "Указан некорректный логин")
    private String login;
    @NotNull()
    private String name;
    @Past(message = "День рождения не может быть позже настоящего времени")
    private LocalDate birthday;
    private Set<Integer> friends;
}

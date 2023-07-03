package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User addUser (User user);
    void deleteUser (User user);
    User updateUser (User newUser);
    List<User> getAllUsers ();
    User getUserById (int id);
}

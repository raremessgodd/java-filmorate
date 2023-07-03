package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NoSuchUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;
    @Override
    public User addUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(id++);
        log.info(String.format("Пользователь %s добален.", user.getLogin()));
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (users.containsKey(user.getId())) {
            log.info(String.format("Пользователь %s удален.", user.getLogin()));
            users.remove(user.getId());
        } else {
            log.warn(String.format("Пользователь %s не найден.", user.getLogin()));
            throw new NoSuchUserException(String.valueOf(user.getId()));
        }
    }

    @Override
    public User updateUser(User newUser) {
        if (newUser.getName() == null || newUser.getName().isEmpty()) {
            newUser.setName(newUser.getLogin());
        }
        if (users.containsKey(newUser.getId())) {
            log.info(String.format("Пользователь %s обновлен.", newUser.getLogin()));
            users.put(newUser.getId(), newUser);
        } else {
            log.warn(String.format("Пользователь %s не найден.", newUser.getLogin()));
            throw new NoSuchUserException(String.valueOf(newUser.getId()));
        }
        return newUser;
    }

    @Override
    public List<User> getAllUsers() {
        log.info(String.format("Количество пользователей: %d", users.size()));
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            log.warn(String.format("Пользователь %s не найден.", id));
            throw new NoSuchUserException(String.valueOf(id));
        }
    }
}

package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    private final UserService service = new UserService();

    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        return service.storage.getAllUsers();
    }

    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User newUser) {
        return service.storage.addUser(newUser);
    }

    @PutMapping(value = "/users")
    public User updateUser(@Valid @RequestBody User newUser) {
        return service.storage.updateUser(newUser);
    }

    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable int id) {
        return service.storage.getUserById(id);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        service.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        service.deleteFriend(id, friendId);
    }

    @GetMapping(value = "/users/{id}/friends")
    public List<User> getUserFriends(@PathVariable int id) {
        return service.getAllFriends(id);
    }

    @GetMapping(value = "/users/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        return service.getAllMutualFriends(id, otherId);
    }
}

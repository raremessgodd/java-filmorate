package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NoSuchUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    public final UserStorage storage = new InMemoryUserStorage();

    public void addFriend (int userId, int friendId) {
        if (userId >= 0 && friendId >= 0) {
            storage.getUserById(userId).getFriends().add(friendId);
            storage.getUserById(friendId).getFriends().add(userId);
        }

        if (userId < 0) {
            throw new NoSuchUserException(String.valueOf(userId));
        } else if (friendId < 0) {
            throw new NoSuchUserException(String.valueOf(friendId));
        }
    }

    public void deleteFriend (int userId, int friendId) {
        if (userId >= 0 && friendId >= 0) {
            storage.getUserById(userId).getFriends().remove(friendId);
            storage.getUserById(friendId).getFriends().remove(userId);
        }

        if (userId < 0) {
            throw new NoSuchUserException(String.valueOf(userId));
        } else if (friendId < 0) {
            throw new NoSuchUserException(String.valueOf(friendId));
        }
    }

    public List<User> getAllMutualFriends (int userId, int otherId) {
        return storage.getAllUsers().stream()
                .filter(user -> user.getFriends().contains(userId)
                        && user.getFriends().contains(otherId))
                .collect(Collectors.toList());
    }

    public List<User> getAllFriends (int userId) {
        return storage.getAllUsers().stream()
                .filter(user1 -> storage.getUserById(userId).getFriends().contains(user1.getId()))
                .collect(Collectors.toList());
    }
}

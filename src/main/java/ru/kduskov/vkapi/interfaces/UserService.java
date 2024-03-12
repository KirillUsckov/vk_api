package ru.kduskov.vkapi.interfaces;

import ru.kduskov.vkapi.model.auth.User;

public interface UserService {
    User findByUsername(String username);
}

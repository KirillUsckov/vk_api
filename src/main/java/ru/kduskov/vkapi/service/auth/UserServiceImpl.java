package ru.kduskov.vkapi.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kduskov.vkapi.interfaces.UserService;
import ru.kduskov.vkapi.model.auth.User;
import ru.kduskov.vkapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

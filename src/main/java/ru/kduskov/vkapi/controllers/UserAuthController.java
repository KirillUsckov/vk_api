package ru.kduskov.vkapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import ru.kduskov.vkapi.model.auth.User;
import ru.kduskov.vkapi.repository.UserRepository;

import javax.swing.plaf.PanelUI;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserAuthController.class);

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String username) {
        logger.info(String.format("DELETE request for auth user with username %s", username));
        if (!userRepository.existsByUsername(username))
            return ResponseEntity.notFound().build();
        userRepository.delete(userRepository.findByUsername(username));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Integer id, @RequestBody User user) {
        logger.info(String.format("PUT request for auth user with id %s", id));
        if (!userRepository.existsById(id))
            return ResponseEntity.notFound().build();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userRepository.save(user));
        return ResponseEntity.ok().body(String.format("User %s was updated", user.getUsername()));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody User user) {
        logger.info(String.format("POST request for auth user %s", user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("User with username " + user.getUsername() + " was already created");
        }
        return ResponseEntity.status(201).body(
                "{\"auth user id\":\"" + userRepository.save(user).getId() + "\"}");
    }
}

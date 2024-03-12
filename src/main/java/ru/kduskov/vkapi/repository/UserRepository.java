package ru.kduskov.vkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kduskov.vkapi.model.auth.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}

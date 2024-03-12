package ru.kduskov.vkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kduskov.vkapi.model.auth.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}

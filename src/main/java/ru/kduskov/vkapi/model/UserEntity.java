package ru.kduskov.vkapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kduskov.vkapi.enums.Role;

@Data
@Entity
@Table(name="users")
@NoArgsConstructor
public class UserEntity {
    @Id
    private int id;
    private String username;
    private String password;
    private int role;

}

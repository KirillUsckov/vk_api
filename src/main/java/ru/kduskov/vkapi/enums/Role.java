package ru.kduskov.vkapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    Permission.POST_UPDATE,
                    Permission.POST_READ,
                    Permission.ALBUM_READ,
                    Permission.USERS_READ
            )
    ), // имеет доступ ко всем обработчикам
    POSTS(Set.of(Permission.POST_UPDATE, Permission.POST_READ)), // имеет доступ к обработчикам /posts/**
    USERS(Set.of(Permission.USERS_READ)), // имеет доступ к обработчикам /users/**
    ALBUMS(Set.of(Permission.ALBUM_READ)); // имеет доступ к обработчикам /albums/**

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getUserAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
               .stream()
               .map(permission -> new SimpleGrantedAuthority(permission.name()))
               .toList();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }

}
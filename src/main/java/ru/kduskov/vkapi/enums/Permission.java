package ru.kduskov.vkapi.enums;

import lombok.Getter;

public enum Permission {
    POST_READ("post:read"),
    POST_UPDATE("post:update"),
    ALBUM_READ("album:read"),
    USERS_READ("users:read");

    @Getter
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}

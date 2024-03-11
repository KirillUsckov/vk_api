package ru.kduskov.vkapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * {
 *     "userId": 1,
 *     "id": 2,
 *     "title": "qui est esse",
 *     "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
 * }
 */
@Getter
@Setter
@AllArgsConstructor
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;
}

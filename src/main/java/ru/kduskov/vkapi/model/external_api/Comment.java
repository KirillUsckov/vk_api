package ru.kduskov.vkapi.model.external_api;

import lombok.*;

/**
 * {
 *         "postId": 1,
 *         "id": 1,
 *         "name": "id labore ex et quam laborum",
 *         "email": "Eliseo@gardner.biz",
 *         "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
 * }
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;
}

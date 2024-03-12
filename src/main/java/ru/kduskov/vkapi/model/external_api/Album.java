package ru.kduskov.vkapi.model.external_api;

import lombok.*;

/**
 * {
 *     "userId": 1,
 *     "id": 2,
 *     "title": "sunt qui excepturi placeat culpa"
 * }
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    private int userId;
    private int id;
    private String title;
}

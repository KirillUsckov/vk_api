package ru.kduskov.vkapi.model.external_api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {
 *     "userId": 1,
 *     "id": 2,
 *     "title": "sunt qui excepturi placeat culpa"
 * }
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    private int userId;
    private int id;
    private String title;
}

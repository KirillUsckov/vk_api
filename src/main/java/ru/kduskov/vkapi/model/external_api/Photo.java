package ru.kduskov.vkapi.model.external_api;

import lombok.*;

/**
 * "albumId": 1,
 *         "id": 2,
 *         "title": "reprehenderit est deserunt velit ipsam",
 *         "url": "https://via.placeholder.com/600/771796",
 *         "thumbnailUrl": "https://via.placeholder.com/150/771796"
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;
}

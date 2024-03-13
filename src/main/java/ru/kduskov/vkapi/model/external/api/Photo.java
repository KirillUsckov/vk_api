package ru.kduskov.vkapi.model.external.api;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    private int id;
    private int albumId;
    private String title;
    private String url;
    private String thumbnailUrl;
}

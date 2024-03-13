package ru.kduskov.vkapi.model.external.api;

import lombok.*;

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

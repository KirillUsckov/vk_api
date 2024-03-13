package ru.kduskov.vkapi.model.external.api;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;
}

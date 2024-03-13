package ru.kduskov.vkapi.model.external.api;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    private int userId;
    private int id;
    private String title;
}

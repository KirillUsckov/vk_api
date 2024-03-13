package ru.kduskov.vkapi.model.external.api;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private String name;
    private String catchPhrase;
    private String bs;
}

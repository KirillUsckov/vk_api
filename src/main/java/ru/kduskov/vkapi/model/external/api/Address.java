package ru.kduskov.vkapi.model.external.api;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class Geo {
        private double lat;
        private double lng;
    }
}

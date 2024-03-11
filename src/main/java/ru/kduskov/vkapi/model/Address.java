package ru.kduskov.vkapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * "address": {
 *  *         "street": "Kulas Light",
 *  *         "suite": "Apt. 556",
 *  *         "city": "Gwenborough",
 *  *         "zipcode": "92998-3874",
 *  *         "geo": {
 *  *             "lat": "-37.3159",
 *  *             "lng": "81.1496"
 *  *         }
 *  *     },
 */
@Getter
@Setter
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

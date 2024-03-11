package ru.kduskov.vkapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {
 * "id": 1,
 * "name": "Leanne Graham",
 * "username": "Bret",
 * "email": "Sincere@april.biz",
 * "address": {
 * "street": "Kulas Light",
 * "suite": "Apt. 556",
 * "city": "Gwenborough",
 * "zipcode": "92998-3874",
 * "geo": {
 * "lat": "-37.3159",
 * "lng": "81.1496"
 * }
 * },
 * "phone": "1-770-736-8031 x56442",
 * "website": "hildegard.org",
 * "company": {
 * "name": "Romaguera-Crona",
 * "catchPhrase": "Multi-layered client-server neural-net",
 * "bs": "harness real-time e-markets"
 * }
 * }
 */
//TODO: make it record
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
}

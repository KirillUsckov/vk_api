package ru.kduskov.vkapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  "company": {
 *  *         "name": "Romaguera-Crona",
 *  *         "catchPhrase": "Multi-layered client-server neural-net",
 *  *         "bs": "harness real-time e-markets"
 *  *     }
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private String name;
    private String catchPhrase;
    private String bs;
}
package org.example.tourplanner.frontend.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenRouteServiceTests {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting with OpenRouteService-tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("OpenRouteService-tests finished");
    }

    // just testing functionality --> this unit test is not representative and can be deleted afterward
//    @Test
//    void checkDate_shouldReturnTrueForRegularDate() {
//        // arrange
//        String address = "Oskar-Morgenstern-Platz 1";
//        OpenRouteService service = new OpenRouteService();
//
//        // act
//        String response = service.searchAddress(address).block();
//        System.out.println(response);
//
//        // assert
//        assertTrue(true);
//    }
}

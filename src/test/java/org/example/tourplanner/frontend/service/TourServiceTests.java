package org.example.tourplanner.frontend.service;

import org.example.tourplanner.frontend.model.Log;
import org.example.tourplanner.frontend.model.Tour;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TourServiceTests {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting with TourService-tests");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("----------------------------------------------------------------------------");
    }

    @AfterEach
    void afterEach() {
        System.out.println("----------------------------------------------------------------------------");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("TourService-tests finished");
    }

    // just for testing of the connection --> can be deleted or refactored
    @Test
    void check() {
        TourService tourService = new TourService();
        Tour[] tours = tourService.getTours().block();
        System.out.println(tours.length);
        assertTrue(true);
    }

}

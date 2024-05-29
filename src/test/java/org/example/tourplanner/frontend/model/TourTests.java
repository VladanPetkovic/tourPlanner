package org.example.tourplanner.frontend.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TourTests {
    Tour tour = new Tour(
            "Vienna city exploration",
            "A beautiful city tour",
            "Stephansplatz",
            "Reumannplatz",
1,
            10.0, // Distance in km
            7200, // Estimated time in seconds
            "Explore the city with a walk through the city"
    );


    @BeforeAll
    void beforeAll() {
        System.out.println("Starting with Tour-tests");
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
    void afterAll() {
        System.out.println("Tour-tests finished");
    }

    @Test
    public void Tour_testInitialization() {
        assertEquals("A beautiful city tour", tour.getDescription());
        assertEquals("Stephansplatz", tour.getFromPlace());
        assertEquals("Reumannplatz", tour.getToPlace());
        assertEquals(TransportType.HIKE.ordinal(), tour.getTransport_type());
        assertEquals(10, tour.getDistance(), 0.001);
        assertEquals(7200, tour.getEstimated_time());
        assertEquals("Explore the city with a walk through the city", tour.getRoute_information());
    }

    @Test
    public void setCombinedTourName_testCombination() {
        // arrange
        String from = "ottakring";
        String to = "hernals";
        String expected_combination = "ottakring-hernals";

        // act
        tour.setCombinedTourName(from, to);

        // assert
        assertEquals(expected_combination, tour.getName());
    }

    @Test
    public void setCombinedTourName_testLowercase() {
        // arrange
        String from = "Ottakring";
        String to = "Hernals";
        String expected_combination = "ottakring-hernals";

        // act
        tour.setCombinedTourName(from, to);

        // assert
        assertEquals(expected_combination, tour.getName());
    }

    @Test
    public void setCombinedTourName_testMissingSpaces() {
        // arrange
        String from = "Ottakring und eine andere Stadt";
        String to = "Hernals und Döbling";
        String expected_combination = "ottakring-und-eine-andere-stadt-hernals-und-döbling";

        // act
        tour.setCombinedTourName(from, to);

        // assert
        assertEquals(expected_combination, tour.getName());
    }
}


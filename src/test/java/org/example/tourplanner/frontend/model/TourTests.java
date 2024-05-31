package org.example.tourplanner.frontend.model;

import org.junit.jupiter.api.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TourTests {
    Tour tour = new Tour(
            "Vienna city exploration",
            "A beautiful city tour",
            "Stephansplatz",
            "Reumannplatz",
            1,
            2000.0, // Distance in m
            20, // Estimated time in minutes
            "Explore the city with a walk through the city"
    );


    @BeforeAll
    void beforeAll() {
        System.out.println("Starting with Tour-tests");
    }

    @AfterAll
    void afterAll() {
        System.out.println("Tour-tests finished");
    }

    @Test
    public void Tour_testInitialization() {
        // arrange
        tour.setDistance(2000.0);
        tour.setEstimated_time(20);

        // assert
        assertEquals("A beautiful city tour", tour.getDescription());
        assertEquals("Stephansplatz", tour.getFromPlace());
        assertEquals("Reumannplatz", tour.getToPlace());
        assertEquals(TransportType.HIKE.ordinal(), tour.getTransport_type());
        assertEquals(2000, tour.getDistance(), 0.001);
        assertEquals(20, tour.getEstimated_time());
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

    @Test
    public void getTransportTypeInteger_testReturnValue() {
        // arrange
        String testString1 = "BIKE";
        String testString2 = "HIKE";
        String testString3 = "RUNNING";
        String testString4 = "VACATION";

        // act
        int transportType1 = Tour.getTransportTypeInteger(testString1);
        int transportType2 = Tour.getTransportTypeInteger(testString2);
        int transportType3 = Tour.getTransportTypeInteger(testString3);
        int transportType4 = Tour.getTransportTypeInteger(testString4);

        // assert
        assertEquals(0, transportType1);
        assertEquals(1, transportType2);
        assertEquals(2, transportType3);
        assertEquals(3, transportType4);
    }

    @Test
    public void getTransportTypeInteger_testWrongString() {
        // arrange
        String invalidString = "hike";

        // act
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            Tour.getTransportTypeInteger(invalidString);
        });

        // assert
        assertEquals("Unexpected value: " + invalidString, exception.getMessage());
    }

    @Test
    public void setPopularity_noLogsExistForTour() {
        // arrange
        Popularity expectedPopularity = Popularity.UNKNOWN;

        // act
        tour.setPopularity(null);

        // assert
        assertEquals(expectedPopularity, tour.getPopularity());
    }

    @Test
    public void setPopularity_lowRatingAndLowRelativeLogCount() {
        // arrange
        TourPopularity tourPopularity = new TourPopularity(1234L, 1L, 10L, 1.0);
        Popularity expectedPopularity = Popularity.UNPOPULAR;

        // act
        tour.setPopularity(tourPopularity);

        // assert
        assertEquals(expectedPopularity, tour.getPopularity());
    }

    @Test
    public void setPopularity_lowRatingAndHighRelativeLogCount() {
        // arrange
        TourPopularity tourPopularity = new TourPopularity(1234L, 10L, 10L, 1.0);
        Popularity expectedPopularity = Popularity.AVERAGE;

        // act
        tour.setPopularity(tourPopularity);

        // assert
        assertEquals(expectedPopularity, tour.getPopularity());
    }

    @Test
    public void setPopularity_avgRatingAndAvgRelativeLogCount() {
        // arrange
        TourPopularity tourPopularity = new TourPopularity(1234L, 5L, 10L, 2.5);
        Popularity expectedPopularity = Popularity.UNPOPULAR;

        // act
        tour.setPopularity(tourPopularity);

        // assert
        assertEquals(expectedPopularity, tour.getPopularity());
    }

    @Test
    public void setPopularity_highRatingAndLowRelativeLogCount() {
        // arrange
        TourPopularity tourPopularity = new TourPopularity(1234L, 1L, 10L, 5.0);
        Popularity expectedPopularity = Popularity.POPULAR;

        // act
        tour.setPopularity(tourPopularity);

        // assert
        assertEquals(expectedPopularity, tour.getPopularity());
    }

    @Test
    public void setPopularity_highRatingAndHighRelativeLogCount() {
        // arrange
        TourPopularity tourPopularity = new TourPopularity(1234L, 10L, 10L, 5.0);
        Popularity expectedPopularity = Popularity.VERY_POPULAR;

        // act
        tour.setPopularity(tourPopularity);

        // assert
        assertEquals(expectedPopularity, tour.getPopularity());
    }

    @Test
    void setChildFriendliness_noDataAvailable() {
        // arrange
        tour.setEstimated_time(60);
        tour.setDistance(5000.0);
        double avgDifficulty = 0.0;     // getting null from backend
        double avgTotalTime = 0.0;      // same here
        double avgDistance = 0.0;       // same here --> no logs available

        // act
        tour.setChildFriendliness(avgDifficulty, avgTotalTime, avgDistance);

        // assert
        assertNull(tour.getChildFriendliness());
    }

    @Test
    void setChildFriendliness_notChildFriendly() {
        // arrange
        tour.setEstimated_time(60);
        tour.setDistance(5000.0);
        double avgDifficulty = 10.0;         // 10 is the maximum difficulty
        double avgTotalTime = 120.0;         // > 1.1 * time
        double avgDistance = 10000.0;        // > 1.1 * distance

        // act
        tour.setChildFriendliness(avgDifficulty, avgTotalTime, avgDistance);

        // assert
        assertEquals(ChildFriendliness.VERY_UNFRIENDLY, tour.getChildFriendliness());
    }

    @Test
    void setChildFriendliness_avgChildFriendly() {
        // arrange
        tour.setEstimated_time(60);
        tour.setDistance(5000.0);
        double avgDifficulty = 5.0;         // 5 is average
        double avgTotalTime = 60.0;         // same as the estimated time
        double avgDistance = 5000.0;        // same as the distance

        // act
        tour.setChildFriendliness(avgDifficulty, avgTotalTime, avgDistance);

        // assert
        assertEquals(ChildFriendliness.NEUTRAL, tour.getChildFriendliness());
    }

    @Test
    void setChildFriendliness_veryChildFriendly() {
        // arrange
        tour.setEstimated_time(60);
        tour.setDistance(5000.0);
        double avgDifficulty = 1.0;         // 1 is the minimum difficulty
        double avgTotalTime = 53.0;         // < 0.9 * time
        double avgDistance = 4400.0;        // < 0.9 * distance

        // act
        tour.setChildFriendliness(avgDifficulty, avgTotalTime, avgDistance);

        // assert
        assertEquals(ChildFriendliness.VERY_FRIENDLY, tour.getChildFriendliness());
    }
}


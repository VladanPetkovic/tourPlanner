/*
package org.example.tourplanner.backend.app.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TourTests {
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
    void exampleTest() {
        assertTrue(true);
    }

    // TODO: implement tests for the creation of the variables
    // check for input validation

    // eg: tourname is combined: "from" + "to"
    // --> test, if the string is correctly made

}
*/


package org.example.tourplanner.backend.app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.example.tourplanner.backend.app.TransportType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TourTests {

    @Test
    public void testViennaTourInitialization() {
        Tour tour = new Tour(
                new SimpleStringProperty("Vienna city exploration"),
                new SimpleStringProperty("A beautiful city tour"),
                new SimpleStringProperty("Stephansplatz"),
                new SimpleStringProperty("Reumannplatz"),
                1, // 1: TransportType.HIKE
                new SimpleDoubleProperty(10), // Distance in km
                new SimpleIntegerProperty(7200), // Estimated time in seconds
                new SimpleStringProperty("Explore the city with a walk through the city")
        );

        assertEquals("Vienna city exploration", tour.getTourName().get());
        assertEquals("A beautiful city tour", tour.getTourDescription().get());
        assertEquals("Stephansplatz", tour.getFrom().get());
        assertEquals("Reumannplatz", tour.getTo().get());
        assertEquals(TransportType.HIKE, tour.getTransportType());
        assertEquals(10, tour.getTourDistance().get(), 0.001);
        assertEquals(7200, tour.getEstimatedTime().get());
        assertEquals("Explore the city with a walk through the city", tour.getRouteInformation().get());
    }

    @Test
    public void testTransportTypeConversion() {
        Tour bikeTour = new Tour(
                new SimpleStringProperty(""), new SimpleStringProperty(""), new SimpleStringProperty(""),
                new SimpleStringProperty(""), 0, null, null, new SimpleStringProperty("")
        );
        assertEquals(TransportType.BIKE, bikeTour.getTransportType());

        Tour hikeTour = new Tour(
                new SimpleStringProperty(""), new SimpleStringProperty(""), new SimpleStringProperty(""),
                new SimpleStringProperty(""), 1, null, null, new SimpleStringProperty("")
        );
        assertEquals(TransportType.HIKE, hikeTour.getTransportType());
    }

    @Test
    public void testPropertySetting() {
        Tour tour = new Tour(
                new SimpleStringProperty(""), new SimpleStringProperty(""), new SimpleStringProperty(""),
                new SimpleStringProperty(""), 0, null, null, new SimpleStringProperty("")
        );

        tour.setTourName(new SimpleStringProperty("New Name"));
        assertEquals("New Name", tour.getTourName().get());

        tour.setTourDistance(new SimpleDoubleProperty(200.0));
        assertEquals(200.0, tour.getTourDistance().get(), 0.001);
    }

    @Test
    public void testInvalidTransportType() {
        Tour invalidTour = new Tour(
                new SimpleStringProperty(""), new SimpleStringProperty(""), new SimpleStringProperty(""),
                new SimpleStringProperty(""), -1, null, null, new SimpleStringProperty("")
        );
        assertEquals(TransportType.VACATION, invalidTour.getTransportType());
    }
}


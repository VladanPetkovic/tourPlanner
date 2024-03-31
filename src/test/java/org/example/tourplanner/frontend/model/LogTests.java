package org.example.tourplanner.frontend.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogTests {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting with Log-tests");
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
        System.out.println("Tour-tests finished");
    }

    @Test
    void checkDate_shouldReturnTrueForRegularDate() {
        // arrange
        String dateTime = "2024-02-12";
        Boolean expectedValue = true;

        // act
        boolean actualValue = Log.checkDate(dateTime);

        // assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void checkDate_shouldReturnFalseForWrongInput() {
        // arrange
        String dateTime = "2024/02/12";
        Boolean expectedValue = false;

        // act
        boolean actualValue = Log.checkDate(dateTime);

        // assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void checkDate_shouldReturnFalseForFutureDate() {
        // arrange
        String dateTime = "20245-02-12";
        Boolean expectedValue = false;

        // act
        boolean actualValue = Log.checkDate(dateTime);

        // assert
        assertEquals(expectedValue, actualValue);
    }

}

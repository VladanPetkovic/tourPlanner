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

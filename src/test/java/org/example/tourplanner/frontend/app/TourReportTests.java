package org.example.tourplanner.frontend.app;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TourReportTests {
    TourReport report = new TourReport();

    @BeforeAll
    void beforeAll() {
        System.out.println("Starting with TourReport-tests");
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
        System.out.println("TourReport-tests finished");
    }

    @Test
    void export_checkEmptyTestVisually() {
        System.out.println("Check: creation of empty test");
        // arrange
        report.setOptions("C:\\Users\\vlada\\Downloads", "");

        // act
        report.export(null);

        // assert
        assertTrue(true);
    }
}

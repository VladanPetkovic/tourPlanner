package org.example.tourplanner.frontend.app;

import org.example.tourplanner.frontend.model.Tour;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ExportTests {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting with Export-tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Export-tests finished");
    }

    @Test
    public void export_errorShouldOccurWhenNoToursGiven() {
        // arrange
        List<Tour> emptyTourList = new ArrayList<>();
        Export export = new Export("C:\\");     // not important, because export should fail

        // act
        boolean hasExported = export.export(emptyTourList);

        // assert
        assertFalse(hasExported);
    }
}

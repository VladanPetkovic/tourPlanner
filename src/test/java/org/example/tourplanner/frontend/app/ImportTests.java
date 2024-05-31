package org.example.tourplanner.frontend.app;

import org.example.tourplanner.frontend.model.Tour;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ImportTests {
    @TempDir
    Path tempDir;
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting with Import-tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Import-tests finished");
    }

    @Test
    public void importToursFromCSV_passingAnEmptyCSV() throws IOException {
        // arrange
        Path emptyCsvFile = tempDir.resolve("empty.csv");
        Files.createFile(emptyCsvFile); // Create an empty CSV file
        Import anImport = new Import(emptyCsvFile.toString());
        List<Tour> tours = new ArrayList<>();

        // act
        tours = anImport.importToursFromCSV();

        // assert
        assertNull(tours);
    }
}

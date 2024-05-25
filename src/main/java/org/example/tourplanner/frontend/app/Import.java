package org.example.tourplanner.frontend.app;

import org.example.tourplanner.frontend.model.Tour;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.tourplanner.frontend.model.Tour.getTransportType;

public class Import {
    private String filePath;

    public Import(String filePath) {
        this.filePath = filePath;
    }

     public List<Tour> importToursFromCSV() {
        List<Tour> tours = new ArrayList<>();
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            // skipping header
            br.readLine();

            while ((line = br.readLine()) != null) {
                Tour tour = getTourFromLine(line, csvSplitBy);

                tours.add(tour);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         return tours;
    }

    private Tour getTourFromLine(String line, String csvSplitBy) {
        String[] tourData = line.split(csvSplitBy);

        return new Tour(
                tourData[0],
                tourData[1],
                tourData[2],
                tourData[3],
                getTransportType(Integer.parseInt(tourData[4])).toString(),
                Double.parseDouble(tourData[5]),
                Integer.parseInt(tourData[6]),
                tourData[7]
        );
    }
}

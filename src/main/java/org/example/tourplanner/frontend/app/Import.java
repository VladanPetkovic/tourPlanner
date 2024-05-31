package org.example.tourplanner.frontend.app;

import org.example.tourplanner.frontend.model.Tour;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Import {
    private String filePath;

    public Import(String filePath) {
        this.filePath = filePath;
    }

     public List<Tour> importToursFromCSV() {
        List<Tour> tours = new ArrayList<>();
        String line;
        String csvSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            // skipping header and checking, if the file is empty
            if (br.readLine() == null) {
                return null;
            }

            while ((line = br.readLine()) != null) {
                Tour tour = getTourFromLine(line, csvSplitBy);

                // return, if import-data invalid
                if (tour == null) {
                    return null;
                }
                tours.add(tour);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         return tours;
    }

    private Tour getTourFromLine(String line, String csvSplitBy) {
        String[] tourData = line.split(csvSplitBy);
        double distance = 0;
        int estimated_time = 0;

        // check count of columns
        if (tourData.length != 8) {
            return null;
        }
        int[] stringColumnIndices = {0, 1, 2, 3, 4, 7};     // string-columns

        for (int index : stringColumnIndices) {
            if (tourData[index].startsWith("\"") && tourData[index].endsWith("\"")) {
                tourData[index] = tourData[index].substring(1, tourData[index].length() - 1);
            } else {
                return null;
            }
        }

        try {
            distance = Double.parseDouble(tourData[5]);
        } catch (NumberFormatException e) {
            return null;
        }

        try {
            estimated_time = Integer.parseInt(tourData[6]);
        } catch (NumberFormatException e) {
            return null;
        }

        return new Tour(
                tourData[0],
                tourData[1],
                tourData[2],
                tourData[3],
                Tour.getTransportTypeInteger(tourData[4]),
                distance,
                estimated_time,
                tourData[7]
        );
    }
}

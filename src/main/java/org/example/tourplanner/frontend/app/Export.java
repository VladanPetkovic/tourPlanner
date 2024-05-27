package org.example.tourplanner.frontend.app;

import org.example.tourplanner.frontend.model.Tour;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class Export {
    private String directoryPath;

    public Export(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public boolean export(List<Tour> tours) {
        try (FileWriter writer = new FileWriter(this.directoryPath + "\\" + createFileName())) {
            // Write CSV header
            writer.append("name;description;fromPlace;toPlace;transport_type;distance;estimated_time;route_information\n");

            // Write tour data
            for (Tour tour : tours) {
                writer.append("\"").append(tour.getName()).append("\"").append(";");
                writer.append("\"").append(tour.getDescription()).append("\"").append(";");
                writer.append("\"").append(tour.getFromPlace()).append("\"").append(";");
                writer.append("\"").append(tour.getToPlace()).append("\"").append(";");
                writer.append("\"").append(tour.getTransportType().toString()).append("\"").append(";");
                writer.append(tour.getDistance().toString()).append(";");
                writer.append(tour.getEstimated_time().toString()).append(";");
                writer.append("\"").append(tour.getRoute_information()).append("\"").append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String createFileName() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        return String.format("tourExport_%04d-%02d-%02d_%02d-%02d-%02d.csv",
                currentDateTime.getYear(),
                currentDateTime.getMonthValue(),
                currentDateTime.getDayOfMonth(),
                currentDateTime.getHour(),
                currentDateTime.getMinute(),
                currentDateTime.getSecond());
    }
}

package org.example.tourplanner.frontend.viewModel;

import org.example.tourplanner.frontend.app.Export;
import org.example.tourplanner.frontend.app.Import;
import org.example.tourplanner.frontend.app.SummarizeReport;
import org.example.tourplanner.frontend.app.TourReport;
import org.example.tourplanner.frontend.model.Tour;
import org.example.tourplanner.frontend.service.TourService;

import java.util.ArrayList;
import java.util.List;

public class ImportExportViewModel {
    private TourService tourService;

    public ImportExportViewModel() {
        tourService = new TourService();
    }

    public boolean exportTours(String directoryPath) {
        Export export = new Export(directoryPath);
        List<Tour> tours = new ArrayList<>();

        Tour[] receivedTours = tourService.getTours().block();
        if (receivedTours != null) {
            tours = List.of(receivedTours);
        }

        return export.export(tours);
    }

    public boolean importTours(String filePath) {
        Import importTours = new Import(filePath);
        List<Tour> toursToImport = importTours.importToursFromCSV();

        Tour[] receivedTours = tourService.createTours(toursToImport).block();
        if (receivedTours != null) {
            return receivedTours.length > 0;
        }
        return false;
    }
}

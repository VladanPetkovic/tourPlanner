package org.example.tourplanner.frontend.viewModel;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.frontend.app.Report;
import org.example.tourplanner.frontend.app.SummarizeReport;
import org.example.tourplanner.frontend.app.TourReport;
import org.example.tourplanner.frontend.model.Tour;
import org.example.tourplanner.frontend.service.TourService;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PdfPreviewViewModel {
    private TourService tourService;
    private Report report;
    private List<Tour> tours = new ArrayList<>();

    public PdfPreviewViewModel() {
        tourService = new TourService();
    }

    public void initializeReport(boolean reportType, Tour selectedTour) {
        tours = new ArrayList<>();
        if (reportType) {
            report = new SummarizeReport();
            Tour[] receivedTours = tourService.getTours().block();
            if (receivedTours != null) {
                tours = List.of(receivedTours);
            }
        } else {
            report = new TourReport();
            if (selectedTour != null) {
                tours.add(selectedTour);
            }
        }
    }

    public ArrayList<Image> getPreviewImages() {
        return report.getPreviewImages(tours);
    }

    public void exportReport(String directory) {
        report.setOptions(directory);
        report.export(tours);
    }
}

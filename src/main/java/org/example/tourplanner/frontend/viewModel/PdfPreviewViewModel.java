package org.example.tourplanner.frontend.viewModel;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.frontend.app.Report;
import org.example.tourplanner.frontend.app.SummarizeReport;
import org.example.tourplanner.frontend.app.TourReport;
import org.example.tourplanner.frontend.model.Log;
import org.example.tourplanner.frontend.model.Tour;
import org.example.tourplanner.frontend.model.TourAverage;
import org.example.tourplanner.frontend.service.LogService;
import org.example.tourplanner.frontend.service.TourService;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PdfPreviewViewModel {
    private TourService tourService;
    private LogService logService;
    private Report report;
    private boolean reportType;
    private Tour tour = null;                                       // used in TourReport
    private List<TourAverage> tourAverages = new ArrayList<>();     // used in SummarizeReport

    public PdfPreviewViewModel() {
        tourService = new TourService();
        logService = new LogService();
    }

    public void initializeReport(boolean reportType, Tour selectedTour) {
        setReportType(reportType);

        if (isReportType()) {
            report = new SummarizeReport();
            TourAverage[] receivedAverages = logService.getAverages().block();
            if (receivedAverages != null) {
                tourAverages = List.of(receivedAverages);
            }
        } else {
            report = new TourReport();
            if (selectedTour != null) {
                Log[] receivedLogs = logService.getLogsByTourId(selectedTour.getTourid()).block();
                tour = selectedTour;
                if (receivedLogs != null) {
                    tour.setLogs(List.of(receivedLogs));
                }
            }
        }
    }

    public ArrayList<Image> getPreviewImages() {
        if (isReportType()) {
            return report.getPreviewImages(tourAverages);
        } else {
            return report.getPreviewImages(tour);
        }
    }

    public void exportReport(String directory) {
        report.setOptions(directory);
        if (isReportType()) {
            report.export(tourAverages);
        } else {
            report.export(tour);
        }
    }
}

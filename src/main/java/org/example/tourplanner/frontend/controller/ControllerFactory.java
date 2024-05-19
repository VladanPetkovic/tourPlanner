package org.example.tourplanner.frontend.controller;

import org.example.tourplanner.frontend.viewModel.ImportExportViewModel;
import org.example.tourplanner.frontend.viewModel.LogViewModel;
import org.example.tourplanner.frontend.viewModel.TourViewModel;

public class ControllerFactory {
    private final ImportExportViewModel importExportViewModel;
    private final LogViewModel logViewModel;
    private final TourViewModel tourViewModel;

    public ControllerFactory() {
        importExportViewModel = new ImportExportViewModel();
        logViewModel = new LogViewModel();
        tourViewModel = new TourViewModel();
    }

    /*
     * Factory-Method Pattern
     */
    public Object create(Class<?> controllerClass) {
        if (controllerClass == ImportExportController.class) {
            return new ImportExportController(importExportViewModel);
        } else if (controllerClass == LogsController.class) {
            return new LogsController(logViewModel, tourViewModel);
        } else if (controllerClass == NavigationController.class) {
            return new NavigationController();
        } else if (controllerClass == ToursController.class) {
            return new ToursController(tourViewModel);
        } else if (controllerClass == ToursEditCreateController.class) {
            return new ToursEditCreateController(tourViewModel);
        }

        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }
}

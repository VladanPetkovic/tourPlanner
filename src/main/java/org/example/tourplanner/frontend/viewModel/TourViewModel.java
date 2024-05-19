package org.example.tourplanner.frontend.viewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.frontend.FocusChangedListener;
import org.example.tourplanner.frontend.model.Tour;
import org.example.tourplanner.frontend.service.TourService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class TourViewModel {
    private List<FocusChangedListener> focusChangedListenerList = new ArrayList<>();
    private final StringProperty currentTourName = new SimpleStringProperty("");
    private final StringProperty currentTourDescription = new SimpleStringProperty("");
    private final StringProperty currentFrom = new SimpleStringProperty("");
    private final StringProperty currentTo = new SimpleStringProperty("");
    private final StringProperty currentTransportType = new SimpleStringProperty();
    private final DoubleProperty currentTourDistance = new SimpleDoubleProperty();
    private final IntegerProperty currentEstimatedTime = new SimpleIntegerProperty(); // in seconds
    private final StringProperty currentRouteInformation = new SimpleStringProperty("");
    private final ObservableList<Tour> tourData = FXCollections.observableArrayList();
    private Tour selectedTour;
    private boolean hasSelectedTour = false;
    private TourService tourService;

    public TourViewModel() {
        // TODO: maybe change the response-handling to be better (what happens when server is inactive?)
        tourService = new TourService();
        Tour[] receivedTours = tourService.getTours().block();
        if (receivedTours != null) {
            Collections.addAll(tourData, receivedTours);
        }
    }

    public void addListener(FocusChangedListener listener) {
        this.focusChangedListenerList.add(listener);
    }

    public void saveDataToList() {
        Tour newTour = new Tour(this.currentTourName.get(),
                this.currentTourDescription.get(),
                this.currentFrom.get(),
                this.currentTo.get(),
                this.currentTransportType.get(),
                this.currentTourDistance.get(),
                this.currentEstimatedTime.get(),
                this.currentRouteInformation.get());

        Tour createdTour = tourService.createTour(newTour).block();
        tourData.add(createdTour);
    }

    public void updateDataInList() {
        if (Objects.equals(this.currentTourName.get(), "")) {
            selectedTour.setCombinedTourName(this.currentFrom.get(), this.currentTo.get());
        } else {
            selectedTour.setName(this.currentTourName.get());
        }
        selectedTour.setDescription(this.currentTourDescription.get());
        selectedTour.setFromPlace(this.currentFrom.get());
        selectedTour.setToPlace(this.currentTo.get());
        selectedTour.initTransportType(this.currentTransportType.get());
        selectedTour.setDistance(this.currentTourDistance.get());
        selectedTour.setEstimated_time(this.currentEstimatedTime.get());
        selectedTour.setRoute_information(this.currentRouteInformation.get());

        tourService.updateTour(selectedTour).block();
    }

    public void deleteTour() {
        tourService.deleteTour(this.selectedTour.getTour_id()).block();
        tourData.remove(this.selectedTour);
    }

    public void resetCurrentInput() {
        currentTourName.set("");
        currentTourDescription.set("");
        currentFrom.set("");
        currentTo.set("");
        currentTransportType.set("");
        currentTourDistance.set(0);
        currentEstimatedTime.set(0);
        currentRouteInformation.set("");
    }

    /**
     * This function checks, if everything is filled out.
     * @return "True", when everything is correct and when something is false, it returns the error message.
     */
    public String checkInput() {
        // check empty properties
        if (Objects.equals(this.currentTourDescription.get(), "") || Objects.equals(this.currentFrom.get(), "") ||
                Objects.equals(this.currentTo.get(), "") || Objects.equals(this.currentTransportType.get(), "") ||
                Objects.equals(this.currentRouteInformation.get(), "")) {
            return "Fill out all required fields!";
        }

        if (this.currentTourDistance.get() == 0) {
            return "Tour distance must be greater than 0!";
        }

        if (this.currentEstimatedTime.get() == 0) {
            return "The estimated time must be greater than 0!";
        }

        return "True";
    }
}

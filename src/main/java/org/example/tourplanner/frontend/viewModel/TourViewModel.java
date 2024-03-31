package org.example.tourplanner.frontend.viewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.frontend.FocusChangedListener;
import org.example.tourplanner.frontend.model.Tour;
import org.example.tourplanner.frontend.model.TransportType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class TourViewModel {
    private List<FocusChangedListener> focusChangedListenerList = new ArrayList<FocusChangedListener>();
    private final StringProperty currentTourName = new SimpleStringProperty("");
    private final StringProperty currentTourDescription = new SimpleStringProperty("");
    private final StringProperty currentFrom = new SimpleStringProperty("");
    private final StringProperty currentTo = new SimpleStringProperty("");
    private final StringProperty currentTransportType = new SimpleStringProperty();
    private final DoubleProperty currentTourDistance = new SimpleDoubleProperty();
    private final IntegerProperty currentEstimatedTime = new SimpleIntegerProperty(); // in seconds
    private final StringProperty currentRouteInformation = new SimpleStringProperty("");
    // some sample data
    private final ObservableList<Tour> tourData = FXCollections.observableArrayList(
                    new Tour("Tour 1", "Description 1", "From 1", "To 1", TransportType.VACATION.name(), 100.0, 120, "Route 1"),
                    new Tour("Tour 2", "Description 2", "From 2", "To 2", TransportType.HIKE.name(), 150.0, 180, "Route 2")
    );
    private Tour selectedTour;
    private boolean hasSelectedTour = false;

    public void addListener(FocusChangedListener listener) {
        this.focusChangedListenerList.add(listener);
    }

    public void saveDataToList() {
        tourData.add(new Tour(this.currentTourName.get(),
                this.currentTourDescription.get(),
                this.currentFrom.get(),
                this.currentTo.get(),
                this.currentTransportType.get(),
                this.currentTourDistance.get(),
                this.currentEstimatedTime.get(),
                this.currentRouteInformation.get()));
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
            return "The estimated time must be greated than 0!";
        }

        return "True";
    }
}

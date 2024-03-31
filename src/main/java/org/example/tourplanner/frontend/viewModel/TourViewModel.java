package org.example.tourplanner.frontend.viewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.frontend.FocusChangedListener;
import org.example.tourplanner.frontend.model.Tour;

import java.util.ArrayList;
import java.util.List;

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
    private final ObservableList<Tour> tourData = FXCollections.observableArrayList();

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

    private void resetCurrentInput() {
        currentTourName.set("");
        currentTourDescription.set("");
        currentFrom.set("");
        currentTo.set("");
        currentTransportType.set("");
        currentTourDistance.set(0);
        currentEstimatedTime.set(0);
        currentRouteInformation.set("");
    }
}

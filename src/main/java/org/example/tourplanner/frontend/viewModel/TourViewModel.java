package org.example.tourplanner.frontend.viewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.backend.app.TransportType;
import org.example.tourplanner.backend.app.model.Tour;
import org.example.tourplanner.frontend.FocusChangedListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TourViewModel {

    private List<FocusChangedListener> focusChangedListenerList = new ArrayList<FocusChangedListener>();

    private final StringProperty tourName = new SimpleStringProperty("");
    private final StringProperty tourDescription = new SimpleStringProperty("");
    private final StringProperty from = new SimpleStringProperty("");
    private final StringProperty to = new SimpleStringProperty("");
    private final IntegerProperty transportType = new SimpleIntegerProperty();
    private final DoubleProperty tourDistance = new SimpleDoubleProperty();
    private final IntegerProperty estimatedTime = new SimpleIntegerProperty(); // in seconds
    private final StringProperty routeInformation = new SimpleStringProperty("");


}

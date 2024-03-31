package org.example.tourplanner.frontend.viewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.frontend.FocusChangedListener;
import org.example.tourplanner.frontend.model.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class LogViewModel {
    private List<FocusChangedListener> focusChangedListenerList = new ArrayList<FocusChangedListener>();
    private final StringProperty currentUsername = new SimpleStringProperty("");
    private final StringProperty currentDateTime = new SimpleStringProperty("");
    private final StringProperty currentComment = new SimpleStringProperty("");
    private final IntegerProperty currentDifficulty = new SimpleIntegerProperty();
    private final DoubleProperty currentTotalDistance = new SimpleDoubleProperty();
    private final IntegerProperty currentTotalTime = new SimpleIntegerProperty();
    private final IntegerProperty currentRating = new SimpleIntegerProperty();
    private final ObservableList<Log> logData = FXCollections.observableArrayList();


    public void addListener(FocusChangedListener listener) {
        this.focusChangedListenerList.add(listener);
    }


    public void saveDataToList() {
        logData.add(new Log(
                this.currentUsername.get(),
                this.currentDateTime.get(),
                this.currentComment.get(),
                this.currentDifficulty.get(),
                this.currentTotalDistance.get(),
                this.currentTotalTime.get(),
                this.currentRating.get()));
    }

    public void resetCurrentInput() {
        this.currentDateTime.set("");
        this.currentComment.set("");
        this.currentDifficulty.set(5);
        this.currentTotalDistance.set(0);
        this.currentTotalTime.set(0);
        this.currentRating.set(2);
    }

    public String checkInput() {
        // check empty properties
        if (Objects.equals(this.currentDateTime.get(), "")) {
            return "Current date is required!";
        }

        if (this.currentTotalDistance.get() == 0) {
            return "The total distance must be greater than 0!";
        }

        if (this.currentTotalTime.get() == 0) {
            return "The total time must be greated than 0!";
        }

        if (!Log.checkDate(this.currentDateTime.get())) {
            return "Input DateTime as: YYYY-MM-DD";
        }

        return "True";
    }
}
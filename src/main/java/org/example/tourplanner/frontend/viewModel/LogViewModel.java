package org.example.tourplanner.frontend.viewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.frontend.FocusChangedListener;
import org.example.tourplanner.frontend.model.Log;
import org.example.tourplanner.frontend.service.LogService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
    private Log selectedLog;
    private LogService logService;

    public LogViewModel() {
        logService = new LogService();
        Log[] receivedLogs = logService.getLogs().block();
        if (receivedLogs != null) {
            Collections.addAll(logData, receivedLogs);
        }
    }


    public void addListener(FocusChangedListener listener) {
        this.focusChangedListenerList.add(listener);
    }


    public void saveDataToList() {
        Log newLog = new Log(
                currentUsername.get(),
                currentDateTime.get(),
                currentComment.get(),
                currentDifficulty.get(),
                currentTotalDistance.get(),
                currentTotalTime.get(),
                currentRating.get());

        Log createdLog = logService.createLog(newLog).block();
        logData.add(createdLog);
    }

    public void updateDataInList() {
        selectedLog.setUsername(currentUsername.get());
        if (Log.checkDate(currentDateTime.get())) {
            selectedLog.setDateTime(LocalDate.parse(currentDateTime.get(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        selectedLog.setComment(currentComment.get());
        selectedLog.setDifficulty(currentDifficulty.get());
        selectedLog.setTotalDistance(currentTotalDistance.get());
        selectedLog.setTotalTime(currentTotalTime.get());
        selectedLog.setRating(currentRating.get());

        logService.updateLog(selectedLog).block();
    }

    public void deleteLog() {
        logService.deleteLog(selectedLog.getLog_id()).block();
        logData.remove(selectedLog);
    }

    public void resetCurrentInput() {
        currentDateTime.set("");
        currentComment.set("");
        currentDifficulty.set(5);
        currentTotalDistance.set(0);
        currentTotalTime.set(0);
        currentRating.set(2);
    }

    public String checkInput() {
        // check empty properties
        if (Objects.equals(currentDateTime.get(), "")) {
            return "Current date is required!";
        }

        if (currentTotalDistance.get() == 0) {
            return "The total distance must be greater than 0!";
        }

        if (currentTotalTime.get() == 0) {
            return "The total time must be greated than 0!";
        }

        if (!Log.checkDate(currentDateTime.get())) {
            return "Input DateTime as: YYYY-MM-DD";
        }

        if (Log.checkDate(currentDateTime.get())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(currentDateTime.get(), formatter);
            if (parsedDate.isAfter(LocalDate.now())) {
                return "Date cannot be in the future!";
            }
        }

        return "True";
    }
}
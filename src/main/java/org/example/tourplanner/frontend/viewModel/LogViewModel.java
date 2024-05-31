package org.example.tourplanner.frontend.viewModel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.frontend.FocusChangedListener;
import org.example.tourplanner.frontend.model.Log;
import org.example.tourplanner.frontend.model.Tour;
import org.example.tourplanner.frontend.service.LogService;
import reactor.core.publisher.Flux;

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
    private final BooleanProperty loading = new SimpleBooleanProperty();
    private Log selectedLog;
    private LogService logService;

    public LogViewModel() {
        logService = new LogService();
        // observe the loading state
        Flux<Boolean> loadingFlux = logService.getLoadingSink().asFlux();
        loadingFlux.subscribe(isLoading -> Platform.runLater(() -> loading.set(isLoading)));
    }

    public void initializeData(Tour selectedTour) {
        logData.clear();
        if (selectedTour == null) {
            return;
        }
        // set initial input
        setInitialInput(selectedTour);
        Log[] receivedLogs = logService.getLogsByTourId(selectedTour.getTourid()).block();
        if (receivedLogs != null) {
            Collections.addAll(logData, receivedLogs);
        }
    }

    public void getLogs(String text, Long tour_id) {
        logService.getLogs(text, tour_id)
                .doOnSubscribe(subscription -> Platform.runLater(() -> loading.set(true)))
                .doOnTerminate(() -> Platform.runLater(() -> loading.set(false)))
                .subscribe(logs -> {
                    if (logs != null) {
                        Platform.runLater(() -> {
                            logData.clear();
                            Collections.addAll(logData, logs);
                        });
                    }
                }, error -> {
                    Platform.runLater(() -> loading.set(false)); // Ensure loading is set to false on error
                });
    }


    public void addListener(FocusChangedListener listener) {
        this.focusChangedListenerList.add(listener);
    }


    public void saveDataToList(Tour selectedTour) {
        Log newLog = new Log(
                currentUsername.get(),
                currentDateTime.get(),
                currentComment.get(),
                currentDifficulty.get(),
                currentTotalDistance.get(),
                currentTotalTime.get(),
                currentRating.get(),
                selectedTour);

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

    public void setInitialInput(Tour selectedTour) {
        currentDateTime.set("");
        currentComment.set("");
        currentDifficulty.set(5);
        currentTotalDistance.set(selectedTour.getDistance());
        currentTotalTime.set(selectedTour.getEstimated_time());
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
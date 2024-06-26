package org.example.tourplanner.frontend.viewModel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.frontend.FocusChangedListener;
import org.example.tourplanner.backend.app.OpenStreetMap;
import org.example.tourplanner.frontend.model.Tour;
import org.example.tourplanner.frontend.model.TourAverage;
import org.example.tourplanner.frontend.model.TourPopularity;
import org.example.tourplanner.frontend.service.LogService;
import org.example.tourplanner.frontend.service.TourService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

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
    private final BooleanProperty loading = new SimpleBooleanProperty();
    private Tour selectedTour;
    private boolean hasSelectedTour = false;
    private TourService tourService;
    private LogService logService;
    private boolean reportType;     // false = tourReport; true = summarizeReport

    public TourViewModel() {
        tourService = new TourService();
        logService = new LogService();
    }

    public void getTours(String searchString) {
        tourService.getTours(searchString)
                .doOnSubscribe(subscription -> Platform.runLater(() -> loading.set(true)))
                .doOnTerminate(() -> Platform.runLater(() -> loading.set(false)))
                .subscribe(receivedTours -> {
                    if (receivedTours != null) {
                        Platform.runLater(() -> {
                            tourData.clear();
                            Collections.addAll(tourData, receivedTours);
                        });
                    }
                }, error -> {
                    Platform.runLater(() -> loading.set(false)); // Ensure loading is set to false on error
                });
    }

    public void addListener(FocusChangedListener listener) {
        this.focusChangedListenerList.add(listener);
    }

    public Image retrieveTourImage() {
        loading.set(true);
        File existingImage = getTourImageFromFolder();
        // open image, if already exists
        if (existingImage.exists()) {
            loading.set(false);
            return new Image(existingImage.toURI().toString());
        }

        // create otherwise and try to open again
        tourService.createTourImage(selectedTour)
                .doOnSubscribe(subscription -> Platform.runLater(() -> loading.set(true)))
                .doOnTerminate(() -> Platform.runLater(() -> loading.set(false)))
                .subscribe(
                        aVoid -> {
                            Platform.runLater(() -> loading.set(false));
                        },
                        error -> {
                            Platform.runLater(() -> loading.set(false));
                        });

        // wait for image creation to complete and then check if image exists
        File newImage = getTourImageFromFolder();
        if (newImage.exists()) {
            return new Image(newImage.toURI().toString());
        }

        // default: return vienna-image
        File defaultImage = new File("src/main/resources/org/example/tourplanner/icons/card.png");
        return new Image(defaultImage.toURI().toString());
    }

    private File getTourImageFromFolder() {
        String imageName = "tour_id_" + selectedTour.getTourid() + ".png";
        String imagePath = "src/main/java/org/example/tourplanner/frontend/pictures/" + imageName;

        return new File(imagePath);
    }

    public String getPopularityString() {
        TourPopularity popularity = logService.getTourPopularity(selectedTour.getTourid()).block();
        selectedTour.setPopularity(popularity);
        return selectedTour.getPopularity().toString();
    }

    public String getChildFriendLinessString() {
        TourAverage tourAverage = logService.getAverages(selectedTour.getTourid()).block();

        if (tourAverage == null) {
            return "No Data";
        }

        if (tourAverage.getAverageDifficulty() == null ||
                tourAverage.getAverageTotalTime() == null ||
                tourAverage.getAverageTotalDistance() == null) {
            return "No Data";
        }

        selectedTour.setChildFriendliness(
                tourAverage.getAverageDifficulty(),
                tourAverage.getAverageTotalTime(),
                tourAverage.getAverageTotalDistance());

        return selectedTour.getChildFriendliness().toString();
    }

    public void saveDataToList() {
        Tour newTour = new Tour(this.currentTourName.get(),
                this.currentTourDescription.get(),
                this.currentFrom.get(),
                this.currentTo.get(),
                Tour.getTransportTypeInteger(this.currentTransportType.get()),
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
        selectedTour.setTransport_type(Tour.getTransportTypeInteger(this.currentTransportType.get()));
        selectedTour.setDistance(this.currentTourDistance.get());
        selectedTour.setEstimated_time(this.currentEstimatedTime.get());
        selectedTour.setRoute_information(this.currentRouteInformation.get());

        tourService.updateTour(selectedTour).block();
    }

    public void deleteTour() {
        tourService.deleteTour(this.selectedTour.getTourid()).block();
        tourData.remove(this.selectedTour);
    }

    public void resetCurrentInput() {
        currentTourName.set("");
        currentTourDescription.set("");
        currentFrom.set("");
        currentTo.set("");
        currentTransportType.set("");
        currentTourDistance.set(1500);
        currentEstimatedTime.set(15);
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

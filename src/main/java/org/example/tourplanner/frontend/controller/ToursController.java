package org.example.tourplanner.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.example.tourplanner.frontend.FocusChangedListener;
import org.example.tourplanner.frontend.model.Tour;
import org.example.tourplanner.frontend.viewModel.TourViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.example.tourplanner.frontend.controller.SwitchScene.switchScene;

public class ToursController implements Initializable {
    private final TourViewModel viewModel;
    public ListView<Tour> tourListView;
    public Label tourNameLabel;
    public Label descriptionLabel;
    public Label fromLabel;
    public Label toLabel;
    public Label transportTypeLabel;
    public Label tourDistanceLabel;
    public Label estimatedTimeLabel;

    public ToursController(TourViewModel tourViewModel) {
        this.viewModel = tourViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel.addListener(new FocusChangedListener() {
            @Override
            public void requestFocusChange(String name) {
                tourListView.requestFocus();
            }
        });

        tourListView.setItems(viewModel.getTourData());
        displayTourNames();
        displayTourInformation();
    }

    /**
     * This function displays only the tour-name in the listView.
     */
    private void displayTourNames() {
        tourListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Tour item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(item.getName()));
                }
            }
        });
    }

    /**
     * This function displays the tour-information in the table on the left hand side.
     */
    private void displayTourInformation() {
        tourListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.viewModel.setSelectedTour(newValue);
                tourNameLabel.setText(newValue.getName());
                descriptionLabel.setText(newValue.getDescription());
                fromLabel.setText(newValue.getFromPlace());
                toLabel.setText(newValue.getToPlace());
                transportTypeLabel.setText(newValue.getTransport_type().name());
                tourDistanceLabel.setText(String.valueOf(newValue.getDistance()));
                estimatedTimeLabel.setText(String.valueOf(newValue.getEstimated_time()));
            } else {
                tourNameLabel.setText("...");
                descriptionLabel.setText("...");
                fromLabel.setText("...");
                toLabel.setText("...");
                transportTypeLabel.setText("...");
                tourDistanceLabel.setText("...");
                estimatedTimeLabel.setText("...");
            }
        });
    }

    public void onCreateNewTourBtnClick(ActionEvent actionEvent) throws IOException {
        this.viewModel.resetCurrentInput();
        this.viewModel.setHasSelectedTour(false);
        this.viewModel.setSelectedTour(null);
        switchScene("sites/tours_editCreate.fxml");
    }
    public void onEditTourBtnClick(ActionEvent actionEvent) throws IOException {
        if (this.viewModel.getSelectedTour() != null) {
            this.viewModel.setHasSelectedTour(true);
            switchScene("sites/tours_editCreate.fxml");
        }
    }

    public void onTourReportBtnClick(ActionEvent actionEvent) {
        // bridge to export to pdf
    }

    public void onSummarizeReportBtnClick(ActionEvent actionEvent) {
        // bridge to export to pdf
    }

    public void onLogBtnClick(ActionEvent actionEvent) throws IOException {
        if (this.viewModel.getSelectedTour() != null) {
            // pass the tour-Name

            // go to the logs page
            switchScene("sites/logs.fxml");
        }
    }

    public void onSearchBtnClick(ActionEvent actionEvent) {
        // some bridge to search logic
    }
}
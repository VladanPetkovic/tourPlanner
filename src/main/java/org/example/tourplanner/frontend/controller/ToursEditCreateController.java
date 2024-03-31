package org.example.tourplanner.frontend.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.tourplanner.frontend.FocusChangedListener;
import org.example.tourplanner.frontend.viewModel.TourViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.example.tourplanner.frontend.controller.SwitchScene.switchScene;

public class ToursEditCreateController implements Initializable {
    private final TourViewModel viewModel;
    private final String[] transportTypeItems = {"Bike", "Hike", "Running", "Vacation"};
    public TextField nameTextField;
    public TextField fromTextField;
    public TextField toTextField;
    public ChoiceBox<String> transportTypeChoiceBox;
    public Spinner<Double> distanceSpinner;
    public Spinner<Integer> estimatedTimeSpinner;
    public TextField routeInformationTextField;
    public TextArea descriptionTextArea;

    public ToursEditCreateController(TourViewModel tourViewModel) {
        this.viewModel = tourViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel.addListener(new FocusChangedListener() {
            @Override
            public void requestFocusChange(String name) {
                nameTextField.requestFocus();
            }
        });
        transportTypeChoiceBox.getItems().addAll(transportTypeItems);

        nameTextField.textProperty().bindBidirectional(viewModel.getCurrentTourName());
        fromTextField.textProperty().bindBidirectional(viewModel.getCurrentFrom());
        toTextField.textProperty().bindBidirectional(viewModel.getCurrentTo());
        transportTypeChoiceBox.valueProperty().bindBidirectional(viewModel.getCurrentTransportType());
        setDistanceSpinner();
        setEstimatedTimeSpinner();
        routeInformationTextField.textProperty().bindBidirectional(viewModel.getCurrentRouteInformation());
        descriptionTextArea.textProperty().bindBidirectional(viewModel.getCurrentTourDescription());
    }

    public void setDistanceSpinner() {
        // min, max, and default values inserted
        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10000.0, 10.0);
        distanceSpinner.setValueFactory(valueFactory);
        valueFactory.valueProperty().bindBidirectional(viewModel.getCurrentTourDistance().asObject());

        this.distanceSpinner.getEditor().addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!isValidDouble(event.getCharacter())) { // Check if the input character is a valid double value
                event.consume(); // Consume the event to prevent non-numeric input
            }
        });
    }

    public void setEstimatedTimeSpinner() {
        // min, max, and default values inserted
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 30);
        estimatedTimeSpinner.setValueFactory(valueFactory);
        valueFactory.valueProperty().bindBidirectional(viewModel.getCurrentEstimatedTime().asObject());

        // Add an event filter to the text field to allow only numeric input
        this.estimatedTimeSpinner.getEditor().addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) { // Only allow digits
                event.consume(); // Consume the event to prevent non-numeric input
            }
        });
    }

    public void onGoBackBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/tours.fxml");
    }

    public void onSaveUpdateBtnClick(ActionEvent actionEvent) throws IOException {
        if (this.viewModel.checkInput().equals("True")) {
            viewModel.saveDataToList();
            switchScene("sites/tours.fxml");
        } else {

        }

    }

    public void onDeleteBtnClick(ActionEvent actionEvent) {

    }

    private static boolean isValidDouble(String text) {
        return text.matches("^\\d+(,)?(\\d*)?$");
    }
}

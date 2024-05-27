package org.example.tourplanner.frontend.controller;

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
    private final String[] transportTypeItems = {"BIKE", "HIKE", "RUNNING", "VACATION"};
    public TextField nameTextField;
    public TextField fromTextField;
    public TextField toTextField;
    public ChoiceBox<String> transportTypeChoiceBox;
    public Spinner<Double> distanceSpinner;
    public Spinner<Integer> estimatedTimeSpinner;
    public TextField routeInformationTextField;
    public TextArea descriptionTextArea;
    public Label errorLabel;

    public ToursEditCreateController(TourViewModel tourViewModel) {
        this.viewModel = tourViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel.addListener(new FocusChangedListener() {
            @Override
            public void requestFocusChange(String name) {
                nameTextField.requestFocus();
                fromTextField.requestFocus();
                toTextField.requestFocus();
                transportTypeChoiceBox.requestFocus();
                distanceSpinner.requestFocus();
                estimatedTimeSpinner.requestFocus();
                routeInformationTextField.requestFocus();
                descriptionTextArea.requestFocus();
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

        if (viewModel.getSelectedTour() != null && viewModel.isHasSelectedTour()) {
            initializeFieldsWithValue();
        }
    }

    private void setDistanceSpinner() {
        // min, max, and default values inserted
        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10000.0, 1, 0.5);
        distanceSpinner.setValueFactory(valueFactory);
        valueFactory.valueProperty().bindBidirectional(viewModel.getCurrentTourDistance().asObject());

        // Get the TextField portion of the Spinner
        TextField textField = distanceSpinner.getEditor();

        // Create a TextFormatter to allow only double input
        TextFormatter<Double> formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("-?\\d*(\\,\\d*)?")) {
                return change;
            } else {
                return null;
            }
        });

        // Set the TextFormatter to the TextField
        textField.setTextFormatter(formatter);
    }

    private void setEstimatedTimeSpinner() {
        // min, max, and default values inserted
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000);
        estimatedTimeSpinner.setValueFactory(valueFactory);
        valueFactory.valueProperty().bindBidirectional(viewModel.getCurrentEstimatedTime().asObject());

        // Add an event filter to the text field to allow only numeric input
        estimatedTimeSpinner.getEditor().addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) { // Only allow digits
                event.consume(); // Consume the event to prevent non-numeric input
            }
        });
    }

    public void onGoBackBtnClick(ActionEvent actionEvent) throws IOException {
        errorLabel.setText("");
        viewModel.setHasSelectedTour(false);
        viewModel.setSelectedTour(null);
        switchScene("sites/tours.fxml");
    }

    public void onSaveUpdateBtnClick(ActionEvent actionEvent) throws IOException {
        if (!viewModel.checkInput().equals("True")) {
            // some inputs have not been filled out
            errorLabel.setText(viewModel.checkInput());
            return;
        }

        if (!viewModel.isHasSelectedTour()) {
            // creating new instance
            viewModel.saveDataToList();
            viewModel.setHasSelectedTour(false);
            viewModel.setSelectedTour(null);
            errorLabel.setText("");
            switchScene("sites/tours.fxml");
        } else {
            // updating
            viewModel.updateDataInList();
            errorLabel.setText("");
            viewModel.setHasSelectedTour(false);
            viewModel.setSelectedTour(null);
            switchScene("sites/tours.fxml");
        }
    }

    public void onDeleteBtnClick(ActionEvent actionEvent) throws IOException {
        errorLabel.setText("");
        viewModel.deleteTour();
        viewModel.setHasSelectedTour(false);
        viewModel.setSelectedTour(null);
        switchScene("sites/tours.fxml");
    }

    /**
     * This function initializes values, when editing or deleting a tour.
     */
    private void initializeFieldsWithValue() {
        nameTextField.setText(viewModel.getSelectedTour().getName());
        fromTextField.setText(viewModel.getSelectedTour().getFromPlace());
        toTextField.setText(viewModel.getSelectedTour().getToPlace());
        transportTypeChoiceBox.setValue(viewModel.getSelectedTour().getTransportType().name());
        distanceSpinner.getValueFactory().setValue(viewModel.getSelectedTour().getDistance());
        estimatedTimeSpinner.getValueFactory().setValue(viewModel.getSelectedTour().getEstimated_time());
        routeInformationTextField.setText(viewModel.getSelectedTour().getRoute_information());
        descriptionTextArea.setText(viewModel.getSelectedTour().getDescription());
    }
}

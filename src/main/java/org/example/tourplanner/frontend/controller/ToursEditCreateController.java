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
    }

    public void setEstimatedTimeSpinner() {
        // min, max, and default values inserted
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 30);
        estimatedTimeSpinner.setValueFactory(valueFactory);
        valueFactory.valueProperty().bindBidirectional(viewModel.getCurrentEstimatedTime().asObject());
    }

    public void onGoBackBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/tours.fxml");
    }

    public void onSaveUpdateBtnClick(ActionEvent actionEvent) throws IOException {
        viewModel.saveDataToList();
        switchScene("sites/tours.fxml");
    }

    public void onDeleteBtnClick(ActionEvent actionEvent) {

    }
}

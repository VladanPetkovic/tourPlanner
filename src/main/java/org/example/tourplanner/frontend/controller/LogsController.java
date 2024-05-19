package org.example.tourplanner.frontend.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.tourplanner.frontend.model.Log;
import org.example.tourplanner.frontend.viewModel.LogViewModel;
import org.example.tourplanner.frontend.viewModel.TourViewModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.example.tourplanner.frontend.controller.SwitchScene.switchScene;

public class LogsController implements Initializable {
    public ListView<Log> logListView;
    public Slider ratingSlider;
    public Spinner<Integer> totalTimeSpinner;
    public Spinner<Double> totalDistanceSpinner;
    public TextField dateTextField;
    public Slider difficultySlider;
    public TextField commentTextField;
    public TextField usernameTextField;
    public Label errorLabel;
    private final LogViewModel viewModel;
    private final TourViewModel tourViewModel;

    public LogsController(LogViewModel logViewModel, TourViewModel tourViewModel) {
        this.viewModel = logViewModel;
        this.tourViewModel = tourViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel.initializeData(tourViewModel.getSelectedTour());

        viewModel.addListener(name -> {
            ratingSlider.requestFocus();
            totalTimeSpinner.requestFocus();
            totalDistanceSpinner.requestFocus();
            dateTextField.requestFocus();
            difficultySlider.requestFocus();
            commentTextField.requestFocus();
            usernameTextField.requestFocus();
        });

        // Binding
        ratingSlider.valueProperty().bindBidirectional(viewModel.getCurrentRating());
        setTimeSpinner(viewModel.getCurrentTotalTime());
        setDistanceSpinner(viewModel.getCurrentTotalDistance());
        dateTextField.textProperty().bindBidirectional(viewModel.getCurrentDateTime());
        difficultySlider.valueProperty().bindBidirectional(viewModel.getCurrentDifficulty());
        commentTextField.textProperty().bindBidirectional(viewModel.getCurrentComment());
        usernameTextField.textProperty().bindBidirectional(viewModel.getCurrentUsername());

        // showing list
        logListView.setItems(viewModel.getLogData());
        displayLogNames();
        displayLogInformation();
    }

    private void displayLogNames() {
        logListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Log item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(item.getDateTime()));
                }
            }
        });
    }

    private void displayLogInformation() {
        logListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.viewModel.setSelectedLog(newValue);
                usernameTextField.setText(newValue.getUsername());
                commentTextField.setText(newValue.getComment());
                difficultySlider.setValue(newValue.getDifficulty());
                dateTextField.setText(String.valueOf(newValue.getDateTime()));
                totalDistanceSpinner.getValueFactory().setValue(newValue.getTotalDistance());
                totalTimeSpinner.getValueFactory().setValue(newValue.getTotalTime());
                ratingSlider.setValue(newValue.getRating());
            } else {
                usernameTextField.setText("...");
                commentTextField.setText("...");
                difficultySlider.setValue(5);
                dateTextField.setText(String.valueOf(LocalDate.now()));
                totalDistanceSpinner.getValueFactory().setValue(1500.0);
                totalTimeSpinner.getValueFactory().setValue(20);
                ratingSlider.setValue(3);
            }
        });
    }

    private void setDistanceSpinner(DoubleProperty doubleProperty) {
        // min, max, and default values inserted
        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10000.0, 1, 0.5);
        totalDistanceSpinner.setValueFactory(valueFactory);
        valueFactory.valueProperty().bindBidirectional(doubleProperty.asObject());

        // Get the TextField portion of the Spinner
        TextField textField = totalDistanceSpinner.getEditor();

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

    private void setTimeSpinner(IntegerProperty integerProperty) {
        // min, max, and default values inserted
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000);
        totalTimeSpinner.setValueFactory(valueFactory);
        valueFactory.valueProperty().bindBidirectional(integerProperty.asObject());

        // Add an event filter to the text field to allow only numeric input
        this.totalTimeSpinner.getEditor().addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) { // Only allow digits
                event.consume(); // Consume the event to prevent non-numeric input
            }
        });
    }

    public void onReturnBtnClick(ActionEvent actionEvent) throws IOException {
        tourViewModel.setSelectedTour(null);
        viewModel.getLogData().clear();
        switchScene("sites/tours.fxml");
    }

    public void onCreateBtnClick(ActionEvent actionEvent) {
        if (viewModel.checkInput().equals("True")) {
            errorLabel.setText("");
            viewModel.saveDataToList(tourViewModel.getSelectedTour());
            viewModel.setSelectedLog(null);
            // TODO: add loosing focus of the list-element
        } else {
            errorLabel.setText(viewModel.checkInput());
        }
    }

    public void onSaveBtnClick(ActionEvent actionEvent) {
        if (viewModel.checkInput().equals("True") && viewModel.getSelectedLog() != null) {
            errorLabel.setText("");
            viewModel.updateDataInList();
        } else {
            errorLabel.setText(viewModel.checkInput());
        }
    }

    public void onDeleteBtnClick(ActionEvent actionEvent) {
        errorLabel.setText("");
        viewModel.deleteLog();
    }
}

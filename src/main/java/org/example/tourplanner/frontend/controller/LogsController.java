package org.example.tourplanner.frontend.controller;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.example.tourplanner.frontend.FocusChangedListener;
import org.example.tourplanner.frontend.model.Log;
import org.example.tourplanner.frontend.model.Tour;
import org.example.tourplanner.frontend.viewModel.LogViewModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.example.tourplanner.frontend.controller.SwitchScene.switchScene;

public class LogsController implements Initializable {
    private final LogViewModel viewModel;
    public ListView<Log> logListView;
    public Slider ratingSlider;
    public Spinner<Integer> totalTimeSpinner;
    public Spinner<Double> totalDistanceSpinner;
    public TextField dateTextField;
    public Slider difficultySlider;
    public TextField commentTextField;
    public TextField usernameTextField;
    public Label errorLabel;

    public LogsController(LogViewModel logViewModel) {
        this.viewModel = logViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        setTimeSpinner(5);
        setDistanceSpinner(5);
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
                setDistanceSpinner(newValue.getTotalDistance());
                setTimeSpinner(newValue.getTotalTime());
                ratingSlider.setValue(newValue.getRating());
            } else {
                usernameTextField.setText("...");
                commentTextField.setText("...");
                difficultySlider.setValue(5);
                dateTextField.setText(String.valueOf(LocalDate.now()));
                setDistanceSpinner(5);
                setTimeSpinner(5);
                ratingSlider.setValue(3);
            }
        });
    }

    private void setDistanceSpinner(double initialValue) {
        // min, max, and default values inserted
        SpinnerValueFactory.DoubleSpinnerValueFactory valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10000.0);
        valueFactory.setValue(initialValue);
        totalDistanceSpinner.setValueFactory(valueFactory);
        valueFactory.valueProperty().bindBidirectional(viewModel.getCurrentTotalDistance().asObject());

        this.totalDistanceSpinner.getEditor().addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!isValidDouble(event.getCharacter())) { // Check if the input character is a valid double value
                event.consume(); // Consume the event to prevent non-numeric input
            }
        });
    }

    private void setTimeSpinner(int initialValue) {
        // min, max, and default values inserted
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000);
        valueFactory.setValue(initialValue);
        totalTimeSpinner.setValueFactory(valueFactory);
        valueFactory.valueProperty().bindBidirectional(viewModel.getCurrentTotalTime().asObject());

        // Add an event filter to the text field to allow only numeric input
        this.totalTimeSpinner.getEditor().addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) { // Only allow digits
                event.consume(); // Consume the event to prevent non-numeric input
            }
        });
    }

    public void onReturnBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/tours.fxml");
    }

    public void onCreateBtnClick(ActionEvent actionEvent) {
        if (viewModel.checkInput().equals("True")) {
            errorLabel.setText("");
            viewModel.saveDataToList();
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

    // TODO: not working properly
    private static boolean isValidDouble(String text) {
        return text.matches("^\\d+(,)?(\\d*)?$");
    }
}

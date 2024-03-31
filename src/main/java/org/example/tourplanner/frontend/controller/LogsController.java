package org.example.tourplanner.frontend.controller;

import javafx.event.ActionEvent;
import org.example.tourplanner.frontend.viewModel.LogViewModel;

import java.io.IOException;

import static org.example.tourplanner.frontend.controller.SwitchScene.switchScene;

public class LogsController {
    private final LogViewModel viewModel;
    public LogsController(LogViewModel logViewModel) {
        this.viewModel = logViewModel;
    }

    public void onReturnBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/tours.fxml");
    }
}

package org.example.tourplanner.frontend.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

import static org.example.tourplanner.frontend.controller.SwitchScene.switchScene;

public class ToursEditCreateController {
    public void onGoBackBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/tours.fxml");
    }
}

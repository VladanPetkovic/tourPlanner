package org.example.tourplanner.frontend.controller;

import javafx.event.ActionEvent;

import java.io.IOException;

import static org.example.tourplanner.frontend.controller.SwitchScene.switchScene;

public class ToursController {
    public void onCreateNewTourBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/tours_editCreate.fxml");
    }
    public void onEditTourBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/tours_editCreate.fxml");
    }
}
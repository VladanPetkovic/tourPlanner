package org.example.tourplanner.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import org.example.tourplanner.Main;

import java.io.IOException;

import static org.example.tourplanner.frontend.controller.SwitchScene.switchScene;

public class ToursController {
    public void onCreateNewTourBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/tours_editCreate.fxml");
    }
}
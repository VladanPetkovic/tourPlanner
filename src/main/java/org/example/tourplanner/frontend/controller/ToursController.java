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

    public void onTourReportBtnClick(ActionEvent actionEvent) {
        // bridge to export to pdf
    }

    public void onSummarizeReportBtnClick(ActionEvent actionEvent) {
        // bridge to export to pdf
    }

    public void onLogBtnClick(ActionEvent actionEvent) throws IOException {
        // go to the logs page
        switchScene("sites/logs.fxml");

        // pass the tour-Name
    }

    public void onSearchBtnClick(ActionEvent actionEvent) {
        // some bridge to search logic
    }
}
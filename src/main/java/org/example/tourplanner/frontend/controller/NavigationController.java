package org.example.tourplanner.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import static org.example.tourplanner.frontend.controller.SwitchScene.switchScene;

public class NavigationController {
    @FXML
    protected void onToursBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/tours.fxml");
    }

    @FXML
    public void onLogsBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/logs.fxml");
    }

    @FXML
    public void onTourReportBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/tour_report.fxml");
    }

    @FXML
    public void onSummarizeReportBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/summarize_report.fxml");
    }

    @FXML
    public void onImportExportBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/import_export.fxml");
    }
}

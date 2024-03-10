package org.example.tourplanner.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.example.tourplanner.Main;

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

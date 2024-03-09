package org.example.tourplanner.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.example.tourplanner.Main;

import java.io.IOException;

public class NavigationController {
    private final double sceneWidth = 920;
    private final double sceneHeight = 700;

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

    public void onImportExportBtnClick(ActionEvent actionEvent) throws IOException {
        switchScene("sites/import_export.fxml");
    }

    private void switchScene(String path) throws IOException {
        Parent newParent = FXMLLoader.load(Main.class.getResource(path));

        // create new scene
        Scene newScene = new Scene(newParent, this.sceneWidth, this.sceneHeight);
        Main.stage.setScene(newScene);
    }
}

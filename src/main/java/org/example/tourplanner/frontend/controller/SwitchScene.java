package org.example.tourplanner.frontend.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.example.tourplanner.Main;

import java.io.IOException;

public class SwitchScene {
    protected static void switchScene(String path) throws IOException {
        double sceneWidth = 920;
        double sceneHeight = 700;

        Parent newParent = FXMLLoader.load(Main.class.getResource(path));

        // create new scene
        Scene newScene = new Scene(newParent, sceneWidth, sceneHeight);
        Main.stage.setScene(newScene);
    }
}

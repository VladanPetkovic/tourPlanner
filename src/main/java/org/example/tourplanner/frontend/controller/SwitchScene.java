package org.example.tourplanner.frontend.controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import org.example.tourplanner.Main;

import java.io.IOException;

public class SwitchScene {
    public static void switchScene(String path) throws IOException {
        double sceneWidth = Main.stage.getWidth();
        double sceneHeight = Main.stage.getHeight();

        Parent newParent = FXMLDependencyInjection.load(path);

        // create new scene
        Scene newScene = new Scene(newParent, sceneWidth, sceneHeight);
        Main.stage.setScene(newScene);
    }
}

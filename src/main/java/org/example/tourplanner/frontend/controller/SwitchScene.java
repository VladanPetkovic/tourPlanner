package org.example.tourplanner.frontend.controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import org.example.tourplanner.Main;

import java.io.IOException;

public class SwitchScene {
    public static void switchScene(String path) throws IOException {
        Parent newParent = FXMLDependencyInjection.load(path);

        // create new scene
        Main.stage.setHeight(Main.stage.getHeight());
        Main.stage.setWidth(Main.stage.getWidth());
        Scene newScene = new Scene(newParent);
        Main.stage.setScene(newScene);
        Main.stage.show();
    }
}

package org.example.tourplanner.frontend.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import org.example.tourplanner.Main;

import java.io.IOException;

public class FXMLDependencyInjection {
    public static Parent load(String location) throws IOException {
        FXMLLoader loader = getLoader(location);
        return loader.load();
    }

    public static FXMLLoader getLoader(String location) {
        return new FXMLLoader(
                Main.class.getResource(location),
                null,
                new JavaFXBuilderFactory(),
                controllerClass-> Main.controllerFactory.create(controllerClass)
        );
    }
}

package org.example.tourplanner;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.tourplanner.frontend.controller.ControllerFactory;
import org.example.tourplanner.frontend.controller.FXMLDependencyInjection;

import java.io.IOException;

public class Main extends Application
{
    public static Stage stage;
    public static ControllerFactory controllerFactory = new ControllerFactory();

    @Override
    public void start(Stage stage) throws IOException
    {
        Main.stage = stage;
        Parent root = FXMLDependencyInjection.load("sites/tours.fxml");
        Scene scene = new Scene(root, 920, 700);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
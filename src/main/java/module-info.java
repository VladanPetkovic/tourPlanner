module org.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires lombok;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.data.jpa;
    requires jakarta.persistence;

    opens org.example.tourplanner to javafx.fxml;
    opens org.example.tourplanner.backend;
    exports org.example.tourplanner;
    exports org.example.tourplanner.frontend.controller;
    opens org.example.tourplanner.frontend.controller to javafx.fxml;
    exports org.example.tourplanner.frontend;
    opens org.example.tourplanner.frontend to javafx.fxml;
    exports org.example.tourplanner.backend;
    exports org.example.tourplanner.backend.controller;
    exports org.example.tourplanner.backend.model;

}
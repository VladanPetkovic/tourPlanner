module org.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires static lombok;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.data.jpa;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires spring.webflux;
    requires reactor.core;
    requires com.fasterxml.jackson.annotation;
    requires layout;
    requires kernel;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires io;
    requires itextpdf;
    requires javafx.swing;

    opens org.example.tourplanner to javafx.fxml;
    opens org.example.tourplanner.backend;
    opens org.example.tourplanner.backend.model;
    opens org.example.tourplanner.frontend.controller to javafx.fxml;
    opens org.example.tourplanner.frontend to javafx.fxml;
    exports org.example.tourplanner;
    exports org.example.tourplanner.frontend.controller;
    exports org.example.tourplanner.frontend.service;
    exports org.example.tourplanner.frontend.model;
    exports org.example.tourplanner.frontend;
    exports org.example.tourplanner.backend;
    exports org.example.tourplanner.backend.controller;
    exports org.example.tourplanner.backend.model;
    exports org.example.tourplanner.backend.service;
}
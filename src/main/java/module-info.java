module org.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires lombok;

    opens org.example.tourplanner to javafx.fxml;
    exports org.example.tourplanner;
    exports org.example.tourplanner.frontend.controller;
    opens org.example.tourplanner.frontend.controller to javafx.fxml;
}
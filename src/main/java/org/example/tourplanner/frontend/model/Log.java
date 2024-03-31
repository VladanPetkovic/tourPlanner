package org.example.tourplanner.frontend.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor     // has already the constructor
public class Log {
    private final StringProperty dateTime;
    private final StringProperty comment;
    private final IntegerProperty difficulty; // only in ranges from 1 to 10
    private final DoubleProperty totalDistance;
    private final DoubleProperty totalTime;
    private final IntegerProperty rating; // only in ranges from 1 to 5

}

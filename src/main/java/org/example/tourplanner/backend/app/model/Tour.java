package org.example.tourplanner.backend.app.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.tourplanner.backend.app.TransportType;

@Getter
@Setter
public class Tour {
    private StringProperty tourName;
    private StringProperty tourDescription;
    private StringProperty from;
    private StringProperty to;
    private TransportType transportType; // need to be converted from int
    private DoubleProperty tourDistance;
    private IntegerProperty estimatedTime; // in seconds
    private StringProperty routeInformation;

    public Tour(StringProperty tourName,
                StringProperty tourDescription,
                StringProperty from,
                StringProperty to,
                int transportType,
                DoubleProperty tourDistance,
                IntegerProperty estimatedTime,
                StringProperty routeInformation) {
        setTourName(tourName);
        setTourDescription(tourDescription);
        setFrom(from);
        setTo(to);
        initTransportType(transportType);
        setTourDistance(tourDistance);
        setEstimatedTime(estimatedTime);
        setRouteInformation(routeInformation);
    }

    private void initTransportType(int transportTypeNumber) {
        switch (transportTypeNumber) {
            case 0: setTransportType(TransportType.BIKE);
                break;
            case 1: setTransportType(TransportType.HIKE);
                break;
            case 2: setTransportType(TransportType.RUNNING);
                break;
            default: setTransportType(TransportType.VACATION);
        }
    }
}

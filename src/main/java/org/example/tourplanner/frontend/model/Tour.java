package org.example.tourplanner.frontend.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Tour {
    private String name;
    private String description;
    private String from;
    private String to;
    private TransportType transportType; // needs to be converted from int
    private Double distance;
    private Integer estimatedTime; // in seconds
    private String routeInformation;

    public Tour(String name,
                String description,
                String from,
                String to,
                String transportType,
                Double distance,
                Integer estimatedTime,
                String routeInformation) {
        if (Objects.equals(name, "")) {
            setCombinedTourName(from, to);
        } else {
            setName(name);
        }
        setDescription(description);
        setFrom(from);
        setTo(to);
        initTransportType(transportType);
        setDistance(distance);
        setEstimatedTime(estimatedTime);
        setRouteInformation(routeInformation);
    }

    private void initTransportType(String transportTypeValue) {
        switch (transportTypeValue) {
            case "Bike": setTransportType(TransportType.BIKE);
                break;
            case "Hike": setTransportType(TransportType.HIKE);
                break;
            case "Running": setTransportType(TransportType.RUNNING);
                break;
            default: setTransportType(TransportType.VACATION);
        }
    }

    /**
     * This function sets the tour-name. It combines From and To to one word.
     * For example: From: Favoriten; To: Ottakring ==> Name: favoriten-ottakring
     */
    private void setCombinedTourName(String from, String to) {
        String combinedName = "";

        if (!Objects.equals(from, "") && !Objects.equals(to, "")) {
            // convert "from" and "to" to lowercase and combine them with "-"
            combinedName = from.toLowerCase() + "-" + to.toLowerCase();
        } else if (!Objects.equals(from, "")) {
            combinedName = from.toLowerCase() + "-";
        } else if (!Objects.equals(to, "")) {
            combinedName = "-" + to.toLowerCase();
        } else {
            System.out.println("ERROR");
        }

        // replace spaces with hyphens
        combinedName = combinedName.replaceAll("\\s+", "-");
        setName(combinedName);
    }
}

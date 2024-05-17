package org.example.tourplanner.frontend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Tour {
    private Long id;
    private String name;
    private String description;
    private String fromPlace;
    private String toPlace;
    private TransportType transport_type; // needs to be converted from int
    private Double distance;
    private Integer estimated_time; // in seconds
    private String route_information;

    public Tour(
                String name,
                String description,
                String fromPlace,
                String toPlace,
                String transport_type,
                Double distance,
                Integer estimated_time,
                String route_information) {
        if (Objects.equals(name, "")) {
            setCombinedTourName(fromPlace, toPlace);
        } else {
            setName(name);
        }
        setId(id);
        setDescription(description);
        setFromPlace(fromPlace);
        setToPlace(toPlace);
        initTransportType(transport_type);
        setDistance(distance);
        setEstimated_time(estimated_time);
        setRoute_information(route_information);
    }

    // Copy constructor
    public Tour(Tour other) {
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
        this.fromPlace = other.fromPlace;
        this.toPlace = other.toPlace;
        this.transport_type = other.transport_type;
        this.distance = other.distance;
        this.estimated_time = other.estimated_time;
        this.route_information = other.route_information;
    }

    public void initTransportType(String transportTypeValue) {
        switch (transportTypeValue) {
            case "Bike": setTransport_type(TransportType.BIKE);
                break;
            case "Hike": setTransport_type(TransportType.HIKE);
                break;
            case "Running": setTransport_type(TransportType.RUNNING);
                break;
            default: setTransport_type(TransportType.VACATION);
        }
    }

    /**
     * This function sets the tour-name. It combines From and To to one word.
     * For example: From: Favoriten; To: Ottakring ==> Name: favoriten-ottakring
     */
    public void setCombinedTourName(String from, String to) {
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

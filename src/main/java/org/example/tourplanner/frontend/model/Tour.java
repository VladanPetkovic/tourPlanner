package org.example.tourplanner.frontend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Tour {
    private Long tourid;
    private String name;
    private String description;
    private String fromPlace;
    private String toPlace;
    private int transport_type;
    private Double distance;
    private Integer estimated_time; // in seconds
    private String route_information;
    private Popularity popularity;
    private ChildFriendliness childFriendliness;
    private List<Log> logs;

    public Tour(
            String name,
            String description,
            String fromPlace,
            String toPlace,
            Integer transport_type,
            Double distance,
            Integer estimated_time,
            String route_information) {
        if (Objects.equals(name, "")) {
            setCombinedTourName(fromPlace, toPlace);
        } else {
            setName(name);
        }
        setDescription(description);
        setFromPlace(fromPlace);
        setToPlace(toPlace);
        setTransport_type(transport_type);
        setDistance(distance);
        setEstimated_time(estimated_time);
        setRoute_information(route_information);
    }

    // Copy constructor
    public Tour(Tour other) {
        this.tourid = other.tourid;
        this.name = other.name;
        this.description = other.description;
        this.fromPlace = other.fromPlace;
        this.toPlace = other.toPlace;
        this.transport_type = other.transport_type;
        this.distance = other.distance;
        this.estimated_time = other.estimated_time;
        this.route_information = other.route_information;
    }

    public static int getTransportTypeInteger(String transportTypeValue) {
        return switch (transportTypeValue) {
            case "BIKE" -> TransportType.BIKE.ordinal();
            case "HIKE" -> TransportType.HIKE.ordinal();
            case "RUNNING" -> TransportType.RUNNING.ordinal();
            default -> TransportType.VACATION.ordinal();
        };
    }

    public TransportType getTransportType() {
        return getTransportType(this.transport_type);
    }

    public static TransportType getTransportType(int transport_type) {
        return switch (transport_type) {
            case 0 -> TransportType.BIKE;
            case 1 -> TransportType.HIKE;
            case 2 -> TransportType.RUNNING;
            default -> TransportType.VACATION;
        };
    }

    // TODO: good for unit-tests
    public void setPopularity(Long numberOfLogs) {
        if (numberOfLogs == 0) {
            this.popularity = Popularity.UNKNOWN;
        } else if (numberOfLogs == 1 || numberOfLogs == 2) {
            this.popularity = Popularity.UNPOPULAR;
        } else if (numberOfLogs == 3 || numberOfLogs == 4) {
            this.popularity = Popularity.AVERAGE;
        } else if (numberOfLogs == 5 || numberOfLogs == 6 || numberOfLogs == 7) {
            this.popularity = Popularity.POPULAR;
        } else {
            this.popularity = Popularity.VERY_POPULAR;
        }
    }

    // TODO: good for unit-tests
    public void setChildFriendliness(double avgDifficulty, double avgTotalTime, double avgDistance) {
        double childFriendLiness = 2;

        if (avgDifficulty == 0 && avgTotalTime == 0 && avgDistance == 0) {
            setChildFriendliness(null);
            return;
        }
        
        // average difficulty
        if (avgDifficulty < 3) {
            childFriendLiness++;
        } else if (avgDifficulty > 7) {
            childFriendLiness--;
        }
        
        // avgTotalTime
        if (avgTotalTime < this.estimated_time * 0.9) {
            childFriendLiness++;
        } else if (avgTotalTime > this.estimated_time * 1.1) {
            childFriendLiness--;
        }

        // avgDistance
        if (avgDistance < this.distance * 0.9) {
            childFriendLiness++;
        } else if (avgDistance > this.distance * 1.1) {
            childFriendLiness--;
        }

        // set childFriendLiness
        setChildFriendliness(ChildFriendliness.fromDouble(childFriendLiness));
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

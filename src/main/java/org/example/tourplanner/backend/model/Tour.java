package org.example.tourplanner.backend.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @Column(name = "tour_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tourid;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "from_place", nullable = false)
    private String fromPlace;
    @Column(name = "to_place", nullable = false)
    private String toPlace;
    @Column(name = "transport_type", nullable = false)
    private int transport_type;
    @Column(name = "distance", nullable = false)
    private double distance;
    @Column(name = "estimated_time", nullable = false)
    private int estimated_time; // in seconds
    @Column(name = "route_information")
    private String route_information;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private final List<Log> logs = new ArrayList<>();

    public Tour() {}

    public Tour(Long id, String name, String description, String from, String to, int transport_type,
                double distance, int estimated_time, String route_information) {
        setTourid(id);
        setName(name);
        setDescription(description);
        setFromPlace(from);
        setToPlace(to);
        setTransport_type(transport_type);
        setDistance(distance);
        setEstimated_time(estimated_time);
        setRoute_information(route_information);
    }
}

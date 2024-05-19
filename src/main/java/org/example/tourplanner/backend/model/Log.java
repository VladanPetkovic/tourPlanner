package org.example.tourplanner.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long log_id;
    @ManyToOne
    @JoinColumn(name = "fk_tour_id", nullable = false, updatable = false)
    @JsonBackReference
    private Tour tour;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "date_time", nullable = false)
    private LocalDate dateTime;
    @Column(name = "comment", nullable = false)
    private String comment;
    @Column(name = "difficulty", nullable = false)
    private Integer difficulty; // only in ranges from 1 to 10
    @Column(name = "total_distance", nullable = false)
    private Double totalDistance;
    @Column(name = "total_time", nullable = false)
    private Integer totalTime;
    @Column(name = "rating", nullable = false)
    private Integer rating; // only in ranges from 1 to 5

    // Constructors
    public Log() {
    }

    public Log(Long log_id, String username, LocalDate dateTime, String comment, Integer difficulty,
               Double totalDistance, Integer totalTime, Integer rating) {
        this.log_id = log_id;
        this.username = username;
        this.dateTime = dateTime;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.rating = rating;
    }
}

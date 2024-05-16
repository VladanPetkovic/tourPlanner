package org.example.tourplanner.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "log")
public class Log {
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public Log(String username, LocalDate dateTime, String comment, Integer difficulty,
               Double totalDistance, Integer totalTime, Integer rating) {
        this.username = username;
        this.dateTime = dateTime;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.rating = rating;
    }

}

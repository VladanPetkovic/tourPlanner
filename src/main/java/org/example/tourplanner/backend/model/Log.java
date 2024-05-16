package org.example.tourplanner.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Log {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private LocalDate dateTime;
    private String comment;
    private Integer difficulty; // only in ranges from 1 to 10
    private Double totalDistance;
    private Integer totalTime;
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

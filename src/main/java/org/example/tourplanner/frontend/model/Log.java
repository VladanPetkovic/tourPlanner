package org.example.tourplanner.frontend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Log {
    private String dateTime;
    private String comment;
    private Integer difficulty; // only in ranges from 1 to 10
    private Double totalDistance;
    private Double totalTime;
    private Integer rating; // only in ranges from 1 to 5

    public Log(String dateTime, String comment, Integer difficulty,
               Double totalDistance, Double totalTime, Integer rating) {
        setDateTime(dateTime);
        setComment(comment);
        setDifficulty(difficulty);
        setTotalDistance(totalDistance);
        setTotalTime(totalTime);
        setRating(rating);
    }
}

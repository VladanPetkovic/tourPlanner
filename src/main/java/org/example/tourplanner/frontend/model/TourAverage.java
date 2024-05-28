package org.example.tourplanner.frontend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourAverage {
    private Long tourid;
    private String name;
    private String fromPlace;
    private String toPlace;
    private Double averageDifficulty;
    private Double averageTotalTime;
    private Double averageTotalDistance;
    private Double averageRating;

    public TourAverage(Long tourid,
                       String name,
                       String fromPlace,
                       String toPlace,
                       Double averageDifficulty,
                       Double averageTotalTime,
                       Double averageTotalDistance,
                       Double averageRating) {
        setTourid(tourid);
        setName(name);
        setFromPlace(fromPlace);
        setToPlace(toPlace);
        setAverageDifficulty(averageDifficulty);
        setAverageTotalTime(averageTotalTime);
        setAverageTotalDistance(averageTotalDistance);
        setAverageRating(averageRating);
    }
}
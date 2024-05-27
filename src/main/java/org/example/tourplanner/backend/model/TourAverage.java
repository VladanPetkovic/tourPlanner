package org.example.tourplanner.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourAverage {
    private Double averageDifficulty;
    private Double averageTotalTime;
    private Double averageTotalDistance;

    public TourAverage(Double averageDifficulty, Double averageTotalTime, Double averageTotalDistance) {
        setAverageDifficulty(averageDifficulty);
        setAverageTotalTime(averageTotalTime);
        setAverageTotalDistance(averageTotalDistance);
    }
}

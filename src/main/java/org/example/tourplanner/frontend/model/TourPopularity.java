package org.example.tourplanner.frontend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourPopularity {
    private Long tourid;
    private Long countOfLogsForTourId;
    private Long maximumCountOfLogs;
    private Double averageRating;

    public TourPopularity(Long tourid, Long countOfLogsForTourId, Long maximumCountOfLogs, Double averageRating) {
        setTourid(tourid);
        setCountOfLogsForTourId(countOfLogsForTourId);
        setMaximumCountOfLogs(maximumCountOfLogs);
        setAverageRating(averageRating);
    }
}

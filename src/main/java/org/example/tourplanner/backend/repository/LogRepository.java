package org.example.tourplanner.backend.repository;

import org.example.tourplanner.backend.model.Log;
import org.example.tourplanner.backend.model.TourAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByTour_Tourid(Long id);

    @Query("SELECT AVG(l.difficulty) FROM Log l WHERE l.tour.tourid = :tourId")
    Double findAverageDifficultyByTourId(@Param("tourId") Long tourId);

    @Query("SELECT AVG(l.totalTime) FROM Log l WHERE l.tour.tourid = :tourId")
    Double findAverageTotalTimeByTourId(@Param("tourId") Long tourId);

    @Query("SELECT AVG(l.totalDistance) FROM Log l WHERE l.tour.tourid = :tourId")
    Double findAverageDistanceByTourId(@Param("tourId") Long tourId);

    @Query("SELECT new org.example.tourplanner.backend.model.TourAverage(AVG(l.difficulty), AVG(l.totalTime), AVG(l.totalDistance)) " +
            "FROM Log l WHERE l.tour.tourid = :tourId")
    TourAverage findAveragesByTourId(@Param("tourId") Long tourId);

    @Query("SELECT COUNT(l) FROM Log l WHERE l.tour.tourid = :tourId")
    Long countLogsByTourId(@Param("tourId") Long tourId);
}

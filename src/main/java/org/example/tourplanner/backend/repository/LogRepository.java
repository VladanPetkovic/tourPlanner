package org.example.tourplanner.backend.repository;

import org.example.tourplanner.backend.model.Log;
import org.example.tourplanner.backend.model.TourAverage;
import org.example.tourplanner.backend.model.TourPopularity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByTour_Tourid(Long id);

    @Query("SELECT l FROM Log l WHERE lower(l.comment) LIKE lower(concat('%', :comment, '%'))")
    List<Log> findByCommentContaining(@Param("comment") String comment);

    @Query("SELECT AVG(l.difficulty) FROM Log l WHERE l.tour.tourid = :tourId")
    Double findAverageDifficultyByTourId(@Param("tourId") Long tourId);

    @Query("SELECT AVG(l.totalTime) FROM Log l WHERE l.tour.tourid = :tourId")
    Double findAverageTotalTimeByTourId(@Param("tourId") Long tourId);

    @Query("SELECT AVG(l.totalDistance) FROM Log l WHERE l.tour.tourid = :tourId")
    Double findAverageDistanceByTourId(@Param("tourId") Long tourId);

    @Query("SELECT new org.example.tourplanner.backend.model.TourAverage(" +
            "l.tour.tourid, " +
            "l.tour.name, " +
            "l.tour.fromPlace, " +
            "l.tour.toPlace, " +
            "AVG(l.difficulty), " +
            "AVG(l.totalTime), " +
            "AVG(l.totalDistance), " +
            "AVG(l.rating)) " +
            "FROM Log l " +
            "GROUP BY l.tour.tourid, l.tour.name, l.tour.fromPlace, l.tour.toPlace " +
            "HAVING l.tour.tourid = :tourId")
    TourAverage findAveragesByTourId(@Param("tourId") Long tourId);

    @Query("SELECT new org.example.tourplanner.backend.model.TourAverage(" +
            "l.tour.tourid, " +
            "l.tour.name, " +
            "l.tour.fromPlace, " +
            "l.tour.toPlace, " +
            "AVG(l.difficulty), " +
            "AVG(l.totalTime), " +
            "AVG(l.totalDistance), " +
            "AVG(l.rating)) " +
            "FROM Log l " +
            "GROUP BY l.tour.tourid, l.tour.name, l.tour.fromPlace, l.tour.toPlace")
    List<TourAverage> findAveragesForAllTours();

    @Query("SELECT AVG(l.rating) FROM Log l WHERE l.tour.tourid = :tourId")
    Double findAverageRatingByTourId(@Param("tourId") Long tourId);

    @Query("SELECT COUNT(l) FROM Log l WHERE l.tour.tourid = :tourId")
    Long countLogsByTourId(@Param("tourId") Long tourId);

    @Query("SELECT new org.example.tourplanner.backend.model.TourPopularity(" +
            "l.tour.tourid, " +
            "COUNT(l), " +
            "(SELECT MAX(sub.count) FROM (SELECT COUNT(subLog) as count FROM Log subLog GROUP BY subLog.tour.tourid) sub), " +
            "AVG(l.rating)) " +
            "FROM Log l WHERE l.tour.tourid = :tourId " +
            "GROUP BY l.tour.tourid")
    TourPopularity findTourPopularity(@Param("tourId") Long tourId);
}

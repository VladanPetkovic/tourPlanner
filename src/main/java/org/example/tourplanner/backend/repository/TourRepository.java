package org.example.tourplanner.backend.repository;

import org.example.tourplanner.backend.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    @Query("SELECT t FROM Tour t WHERE lower(t.name) LIKE lower(concat('%', :name, '%'))")
    List<Tour> findByNameContaining(@Param("name") String name);

    List<Tour> findTop100ByOrderByIdDesc();
}

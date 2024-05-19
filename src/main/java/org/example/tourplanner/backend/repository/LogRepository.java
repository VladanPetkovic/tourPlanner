package org.example.tourplanner.backend.repository;

import org.example.tourplanner.backend.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByTour_Tourid(Long id);
}

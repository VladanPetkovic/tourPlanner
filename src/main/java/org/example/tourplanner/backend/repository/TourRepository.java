package org.example.tourplanner.backend.repository;

import org.example.tourplanner.backend.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
}

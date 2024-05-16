package org.example.tourplanner.backend.repository;

import org.example.tourplanner.backend.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}

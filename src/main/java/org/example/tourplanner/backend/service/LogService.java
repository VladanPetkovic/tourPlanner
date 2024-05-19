package org.example.tourplanner.backend.service;

import org.example.tourplanner.backend.model.Log;
import org.example.tourplanner.backend.model.Tour;
import org.example.tourplanner.backend.repository.LogRepository;
import org.example.tourplanner.backend.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {
    private final LogRepository logRepository;
    private final TourRepository tourRepository;

    @Autowired
    public LogService(LogRepository logRepository, TourRepository tourRepository) {
        this.logRepository = logRepository;
        this.tourRepository = tourRepository;
    }

    public Log saveLog(Long tourId, Log log) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new RuntimeException("Tour not found"));
        log.setTour(tour);
        return logRepository.save(log);
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    public List<Log> getAllByTourId(Long tour_id) {
        return logRepository.findByTour_Tourid(tour_id);
    }

    public Optional<Log> getLogById(Long id) {
        return logRepository.findById(id);
    }

    public Log updateLog(Long id, Log logDetails) {
        Optional<Log> optionalLog = logRepository.findById(id);
        if (optionalLog.isPresent()) {
            Log log = optionalLog.get();
            log.setUsername(logDetails.getUsername());
            log.setDateTime(logDetails.getDateTime());
            log.setComment(logDetails.getComment());
            log.setDifficulty(logDetails.getDifficulty());
            log.setTotalDistance(logDetails.getTotalDistance());
            log.setTotalTime(logDetails.getTotalTime());
            log.setRating(logDetails.getRating());
            return logRepository.save(log);
        } else {
            throw new RuntimeException("Log not found");
        }
    }

    public void deleteLog(Long id) {
        logRepository.deleteById(id);
    }
}

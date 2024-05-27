package org.example.tourplanner.backend.service;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.tourplanner.backend.model.Log;
import org.example.tourplanner.backend.model.Tour;
import org.example.tourplanner.backend.model.TourAverage;
import org.example.tourplanner.backend.repository.LogRepository;
import org.example.tourplanner.backend.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {
    private static final Logger logger = LogManager.getLogger(LogService.class);
    private final LogRepository logRepository;
    private final TourRepository tourRepository;

    @Autowired
    public LogService(LogRepository logRepository, TourRepository tourRepository) {
        this.logRepository = logRepository;
        this.tourRepository = tourRepository;
    }

    public Log saveLog(Long tourId, Log log) {
        logger.info("Attempting to save log for tour ID: {}", tourId);
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> {
            logger.error("Tour not found with ID: {}", tourId);
            return new RuntimeException("Tour not found");
        });
        log.setTour(tour);
        Log savedLog = logRepository.save(log);
        logger.info("Log saved successfully with ID: {}", savedLog.getLog_id());
        return savedLog;
    }

    public List<Log> getAllLogs() {
        logger.info("Fetching all logs");
        List<Log> logs = logRepository.findAll();
        logger.info("Retrieved total logs: {}", logs.size());
        return logs;
    }

    public List<Log> getAllByTourId(Long tour_id) {
        logger.info("Fetching logs for tour ID: {}", tour_id);
        List<Log> logs = logRepository.findByTour_Tourid(tour_id);
        logger.info("Retrieved logs for tour ID: {}, count: {}", tour_id, logs.size());
        return logs;
    }

    public Optional<Log> getLogById(Long id) {
        logger.info("Fetching log by ID: {}", id);
        Optional<Log> log = logRepository.findById(id);
        if (log.isPresent()) {
            logger.info("Log found with ID: {}", id);
        } else {
            logger.warn("Log not found with ID: {}", id);
        }
        return log;
    }

    public Log updateLog(Long id, Log logDetails) {
        logger.info("Attempting to update log with ID: {}", id);
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
            Log updatedLog = logRepository.save(log);
            logger.info("Log updated successfully for ID: {}", id);
            return updatedLog;
        } else {
            logger.error("Failed to find log with ID: {}", id);
            throw new RuntimeException("Log not found");
        }
    }

    public void deleteLog(Long id) {
        logger.info("Attempting to delete log with ID: {}", id);
        try {
            logRepository.deleteById(id);
            logger.info("Log deleted successfully ID: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete log ID: {}", id, e);
            throw e;
        }
    }

    public Double getAverageDifficulty(Long tourId) {
        return logRepository.findAverageDifficultyByTourId(tourId);
    }
    public Double getAverageTotalTime(Long tourId) {
        return logRepository.findAverageTotalTimeByTourId(tourId);
    }
    public Double getAverageTotalDistance(Long tourId) {
        return logRepository.findAverageDistanceByTourId(tourId);
    }

    public TourAverage getAverages(Long tourId) {
        return logRepository.findAveragesByTourId(tourId);
    }

    public Long countLogsByTourId(Long tourId) {
        return logRepository.countLogsByTourId(tourId);
    }
}

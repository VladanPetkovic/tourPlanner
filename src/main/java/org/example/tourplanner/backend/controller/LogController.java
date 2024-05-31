package org.example.tourplanner.backend.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.tourplanner.backend.model.Log;
import org.example.tourplanner.backend.model.Tour;
import org.example.tourplanner.backend.model.TourAverage;
import org.example.tourplanner.backend.model.TourPopularity;
import org.example.tourplanner.backend.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs")
public class LogController {
    private static final Logger logger = LogManager.getLogger(LogController.class);
    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Log>> searchLogsByComment(@RequestParam String comment) {
        List<Log> logs = logService.findByCommentContaining(comment);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Log> saveLog(@RequestParam Long tourId, @RequestBody Log log) {
        logger.info("Attempting to save a new log for tour ID: {}", tourId);
        try {
            Log newLog = logService.saveLog(tourId, log);
            logger.info("Log created successfully with ID: {}", newLog.getLog_id());
            return new ResponseEntity<>(newLog, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to save log for tour ID: {}", tourId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Log>> getAllLogs() {
        logger.info("Fetching all logs");
        try {
            List<Log> logs = logService.getAllLogs();
            return new ResponseEntity<>(logs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve all logs", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logsByTour")
    public ResponseEntity<List<Log>> getLogsByTourId(@RequestParam Long tour_id) {
        logger.info("Fetching logs for tour ID: {}", tour_id);
        try {
            List<Log> logs = logService.getAllByTourId(tour_id);
            return new ResponseEntity<>(logs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to fetch logs for tour ID: {}", tour_id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Log> getLogById(@PathVariable Long id) {
        logger.info("Fetching log by ID: {}", id);
        Optional<Log> log = logService.getLogById(id);
        return log.map(l -> {
            logger.info("Log found with ID: {}", id);
            return ResponseEntity.ok(l);
        }).orElseGet(() -> {
            logger.warn("No log found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Log> updateLog(@PathVariable Long id, @RequestBody Log logDetails) {
        logger.info("Attempting to update log with ID: {}", id);
        try {
            Log updatedLog = logService.updateLog(id, logDetails);
            logger.info("Log updated successfully for ID: {}", id);
            return new ResponseEntity<>(updatedLog, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to update log for ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLog(@PathVariable Long id) {
        logger.info("Attempting to delete log with ID: {}", id);
        try {
            logService.deleteLog(id);
            logger.info("Log deleted successfully for ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Failed to delete log with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** NOT NEEDED EXPLICITLY, BUT STILL IMPLEMENTED **/
    @GetMapping("/average-difficulty")
    public ResponseEntity<Double> getAverageDifficulty(@RequestParam Long tourId) {
        logger.info("Fetching average difficulty for tour ID: {}", tourId);
        try {
            Double averageDifficulty = logService.getAverageDifficulty(tourId);
            return new ResponseEntity<>(averageDifficulty, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve average difficulty for tour ID: {}", tourId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/average-total-time")
    public ResponseEntity<Double> getAverageTotalTime(@RequestParam Long tourId) {
        logger.info("Fetching average total time for tour ID: {}", tourId);
        try {
            Double averageDifficulty = logService.getAverageTotalTime(tourId);
            return new ResponseEntity<>(averageDifficulty, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve average total time for tour ID: {}", tourId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/average-total-distance")
    public ResponseEntity<Double> getAverageTotalDistance(@RequestParam Long tourId) {
        logger.info("Fetching average total distance for tour ID: {}", tourId);
        try {
            Double averageDifficulty = logService.getAverageTotalDistance(tourId);
            return new ResponseEntity<>(averageDifficulty, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve average total distance for tour ID: {}", tourId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/average-total-rating")
    public ResponseEntity<Double> getAverageTotalRating(@RequestParam Long tourId) {
        logger.info("Fetching average rating for tour ID: {}", tourId);
        try {
            Double averageRating = logService.getAverageRating(tourId);
            return new ResponseEntity<>(averageRating, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve average rating for tour ID: {}", tourId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/averages")
    public ResponseEntity<TourAverage> getAverages(@RequestParam Long tourId) {
        logger.info("Fetching averages for tour ID: {}", tourId);
        try {
            TourAverage averages = logService.getAverages(tourId);
            return new ResponseEntity<>(averages, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve averages for tour ID: {}", tourId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/averagesAll")
    public ResponseEntity<List<TourAverage>> getAverages() {
        logger.info("Fetching averages for all tours.");
        try {
            List<TourAverage> averages = logService.getAverages();
            return new ResponseEntity<>(averages, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve averages for all tours.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/popularity")
    public ResponseEntity<TourPopularity> getTourPopularity(@RequestParam Long tourId) {
        logger.info("Fetching popularity for tour ID: {}", tourId);
        try {
            TourPopularity popularity = logService.getTourPopularity(tourId);
            return new ResponseEntity<>(popularity, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve popularity for tour ID: {}", tourId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countLogsByTourId(@RequestParam Long tourId) {
        logger.info("Fetching log count for tour ID: {}", tourId);
        try {
            Long count = logService.countLogsByTourId(tourId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve log count for tour ID: {}", tourId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

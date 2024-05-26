package org.example.tourplanner.backend.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.tourplanner.backend.model.Log;
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
}

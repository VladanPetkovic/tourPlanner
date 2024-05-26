package org.example.tourplanner.backend.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.tourplanner.backend.model.Tour;
import org.example.tourplanner.backend.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tours")
public class TourController {
    private static final Logger logger = LogManager.getLogger(TourController.class);
    private final TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping
    public ResponseEntity<Tour> saveTour(@RequestBody Tour tour) {
        logger.info("Attempting to create a new tour: {}", tour.getName());
        try {
            Tour newTour = tourService.saveTour(tour);
            logger.info("Tour created successfully with ID: {}", newTour.getTourid());
            return new ResponseEntity<>(newTour, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to create tour: {}", tour.getName(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Tour>> createTours(@RequestBody List<Tour> tours) {
        logger.info("Attempting to create a batch of tours");
        try {
            List<Tour> newTours = tourService.saveTours(tours);
            logger.info("Batch of tours created successfully, count: {}", newTours.size());
            return new ResponseEntity<>(newTours, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to create a batch of tours", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Tour>> getAllTours() {
        logger.info("Fetching all tours");
        try {
            List<Tour> tours = tourService.getAllTours();
            logger.info("Retrieved all tours, total count: {}", tours.size());
            return new ResponseEntity<>(tours, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve all tours", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Long id) {
        logger.info("Fetching tour by ID: {}", id);
        Optional<Tour> tour = tourService.getTourById(id);
        return tour.map(t -> {
            logger.info("Tour found: ID {}", id);
            return ResponseEntity.ok(t);
        }).orElseGet(() -> {
            logger.warn("Tour not found for ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Long id, @RequestBody Tour tour) {
        logger.info("Attempting to update tour with ID: {}", id);
        try {
            Tour updatedTour = tourService.updateTour(id, tour);
            logger.info("Tour updated successfully for ID: {}", id);
            return new ResponseEntity<>(updatedTour, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to update tour for ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTour(@PathVariable Long id) {
        logger.info("Attempting to delete tour with ID: {}", id);
        try {
            tourService.deleteTour(id);
            logger.info("Tour deleted successfully for ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Failed to delete tour with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

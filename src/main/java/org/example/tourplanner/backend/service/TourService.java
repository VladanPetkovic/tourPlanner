package org.example.tourplanner.backend.service;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.tourplanner.backend.model.Tour;
import org.example.tourplanner.backend.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    private static final Logger logger = LogManager.getLogger(TourService.class);
    private final TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    /**
     * Save a tour.
     * @param tour the tour to save
     * @return the persisted entity
     */
    public Tour saveTour(Tour tour) {
        logger.info("Attempting to save tour: {}", tour.getName());
        Tour savedTour = tourRepository.save(tour);
        logger.info("Tour saved successfully with ID: {}", savedTour.getTourid());
        return savedTour;
    }

    public List<Tour> saveTours(List<Tour> tours) {
        logger.info("Attempting to save a batch of tours, count: {}", tours.size());
        List<Tour> savedTours = tourRepository.saveAll(tours);
        logger.info("Batch of tours saved successfully, total: {}", savedTours.size());
        return savedTours;
    }

    /**
     * Get all tours.
     * @return the list of entities.
     */
    public List<Tour> getAllTours() {
        logger.info("Fetching all tours");
        List<Tour> tours = tourRepository.findAll();
        logger.info("Retrieved all tours, count: {}", tours.size());
        return tours;
    }

    /**
     * Get one tour by ID.
     * @param id the ID of the tour.
     * @return the entity
     */
    public Optional<Tour> getTourById(Long id) {
        logger.info("Fetching tour by ID: {}", id);
        Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isPresent()) {
            logger.info("Tour found with ID: {}", id);
        } else {
            logger.warn("Tour not found with ID: {}", id);
        }
        return tour;
    }

    public Tour updateTour(Long id, Tour updatedTour) {
        logger.info("Attempting to update tour with ID: {}", id);
        Optional<Tour> existingTour = tourRepository.findById(id);
        if (existingTour.isPresent()) {
             Tour tour = existingTour.get();
            // Update tour properties
            tour.setName(updatedTour.getName());
            tour.setDescription(updatedTour.getDescription());
            tour.setFromPlace(updatedTour.getFromPlace());
            tour.setToPlace(updatedTour.getToPlace());
            tour.setTransport_type(updatedTour.getTransport_type());
            tour.setDistance(updatedTour.getDistance());
            tour.setEstimated_time(updatedTour.getEstimated_time());
            tour.setRoute_information(updatedTour.getRoute_information());
            Tour savedTour = tourRepository.save(tour);
            logger.info("Tour updated successfully for ID: {}", id);
            return savedTour;
        } else {
            logger.error("Failed to find tour with ID: {}", id);
            throw new RuntimeException("Tour not found");
        }
    }

    public void deleteTour(Long id) {
        logger.info("Attempting to delete tour with ID: {}", id);
        try {
            tourRepository.deleteById(id);
            logger.info("Tour deleted successfully ID: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete tour with ID: {}", id, e);
            throw e;
        }
    }
}

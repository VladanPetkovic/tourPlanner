package org.example.tourplanner.backend.service;

import org.example.tourplanner.backend.model.Tour;
import org.example.tourplanner.backend.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {
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
        return tourRepository.save(tour);
    }

    public List<Tour> saveTours(List<Tour> tours) {
        return tourRepository.saveAll(tours);
    }

    /**
     * Get all tours.
     * @return the list of entities.
     */
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    /**
     * Get one tour by ID.
     * @param id the ID of the tour.
     * @return the entity
     */
    public Optional<Tour> getTourById(Long id) {
        return tourRepository.findById(id);
    }

    public Tour updateTour(Long id, Tour updatedTour) {
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
            return tourRepository.save(tour);
        } else {
            throw new RuntimeException("Tour not found");
        }
    }

    public void deleteTour(Long id) {
        tourRepository.deleteById(id);
    }
}

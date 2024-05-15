package org.example.tourplanner.backend.controller;


import org.example.tourplanner.backend.model.Tour;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/tours")
public class TourController {

    private final List<Tour> tours = Arrays.asList(
            new Tour(1L, "Tour 1", "Description 1"),
            new Tour(2L, "Tour 2", "Description 2")
    );

    @GetMapping
    public ResponseEntity<List<Tour>> getAllTours() {
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Long id) {
        return tours.stream()
                .filter(tour -> tour.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

package org.example.tourplanner.frontend.service;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.tourplanner.frontend.model.Tour;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.List;

@Service
public class TourService {
    private static final Logger logger = LogManager.getLogger(TourService.class);
    private static final String BASE_URL = "http://localhost:8080/tours";

    private final WebClient webClient;

    public TourService() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    public Mono<Tour> createTour(Tour tour) {
        logger.info("Creating tour: {}", tour.getName());
        return webClient.post()
                .bodyValue(tour)
                .retrieve()
                .bodyToMono(Tour.class)
                .doOnSuccess(createdTour -> logger.info("Tour created successfully with ID: {}", createdTour.getTourid()))
                .doOnError(error -> logger.error("Failed to create tour", error));
    }

    public Mono<Tour[]> createTours(List<Tour> tours) {
        logger.info("Creating batch of tours, count: {}", tours.size());
        return webClient.post()
                .uri("/batch")
                .bodyValue(tours)
                .retrieve()
                .bodyToMono(Tour[].class)
                .doOnSuccess(createdTours -> logger.info("Batch of tours created successfully, total: {}", createdTours.length))
                .doOnError(error -> logger.error("Failed to create batch of tours", error));
    }

    public Mono<Tour[]> getTours() {
        logger.info("Fetching all tours");
        return webClient.get()
                .retrieve()
                .bodyToMono(Tour[].class)
                .doOnSuccess(tours -> logger.info("Fetched all tours, count: {}", tours.length))
                .doOnError(error -> logger.error("Failed to fetch tours", error));
    }

    public Mono<Tour[]> getTours(String searchString) {
        logger.info("Fetching all tours");
        return webClient.get()
                .uri("/search?search=" + searchString)
                .retrieve()
                .bodyToMono(Tour[].class)
                .flatMap(tours -> simulateLatency().then(Mono.just(tours)))
                .doOnSuccess(tours -> {
                    logger.info("Searching for tours, count: {}", tours.length);
                })
                .doOnError(error -> {
                    logger.error("Failed to search for tours", error);
                });
    }

    public Mono<Tour> getTour(long id) {
        logger.info("Fetching tour by ID: {}", id);
        return webClient.get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Tour.class)
                .doOnSuccess(tour -> logger.info("Fetched tour with ID: {}", tour.getTourid()))
                .doOnError(error -> logger.error("Failed to fetch tour by ID: {}", id, error));
    }

    public Mono<Tour> updateTour(Tour tour) {
        logger.info("Updating tour with ID: {}", tour.getTourid());
        return webClient.put()
                .uri("/" + tour.getTourid())
                .bodyValue(tour)
                .retrieve()
                .bodyToMono(Tour.class)
                .doOnSuccess(updatedTour -> logger.info("Tour updated successfully for ID: {}", updatedTour.getTourid()))
                .doOnError(error -> logger.error("Failed to update tour with ID: {}", tour.getTourid(), error));
    }

    public Mono<Void> deleteTour(long id) {
        logger.info("Deleting tour with ID: {}", id);
        return webClient.delete()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(aVoid -> logger.info("Tour deleted successfully ID: {}", id))
                .doOnError(error -> logger.error("Failed to delete tour with ID: {}", id, error));
    }

    private Mono<Void> simulateLatency() {
        return Mono.delay(Duration.ofSeconds(0))
                .then()
                .doOnSubscribe(subscription -> logger.info("Simulating latency..."))
                .doOnTerminate(() -> logger.info("Latency simulation completed"));
    }
}

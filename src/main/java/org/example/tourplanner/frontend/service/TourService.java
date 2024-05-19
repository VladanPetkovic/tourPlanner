package org.example.tourplanner.frontend.service;

import org.example.tourplanner.frontend.model.Tour;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TourService {
    private static final String BASE_URL = "http://localhost:8080/tours";

    private final WebClient webClient;

    public TourService() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    public Mono<Tour> createTour(Tour tour) {
        return webClient.post()
                .bodyValue(tour)
                .retrieve()
                .bodyToMono(Tour.class);
    }

    public Mono<Tour[]> getTours() {
        return webClient.get()
                .retrieve()
                .bodyToMono(Tour[].class);
    }

    public Mono<Tour> getTour(long id) {
        return webClient.get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Tour.class);
    }

    public Mono<Tour> updateTour(Tour tour) {
        return webClient.put()
                .uri("/" + tour.getTour_id())
                .bodyValue(tour)
                .retrieve()
                .bodyToMono(Tour.class);
    }

    public Mono<Void> deleteTour(long id) {
        return webClient.delete()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
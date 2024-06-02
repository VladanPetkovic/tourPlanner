package org.example.tourplanner.frontend.service;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class OpenRouteService {
    private static final Logger logger = LogManager.getLogger(LogService.class);
    @Getter
    private final Sinks.Many<Boolean> loadingSink = Sinks.many().replay().latest();
    private static final String GEOCODE_URL = "https://api.openrouteservice.org/geocode/";
    /** LEAVING THIS KEY ON PURPOSE, SO THE PROJECT CAN BE TESTED, WITHOUT SETTING UP A NEW KEY **/
    private static final String API_KEY = "5b3ce3597851110001cf62484145947120374713905633154bb7acc3";
    private final WebClient webClient;

    public OpenRouteService() {
        this.webClient = WebClient.builder().baseUrl(GEOCODE_URL).build();
    }

    public Mono<String> searchAddress(String address) {
        String url = "search";
        loadingSink.tryEmitNext(true);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("api_key", API_KEY)
                        .queryParam("text", address)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> {
                    loadingSink.tryEmitNext(false);
                    logger.info("Search for address: {} completed successfully", address);
                })
                .doOnError(error -> {
                    loadingSink.tryEmitNext(false);
                    logger.error("Error occurred while searching for address: {}", address, error);
                });
    }
}

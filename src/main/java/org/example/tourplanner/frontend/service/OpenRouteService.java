package org.example.tourplanner.frontend.service;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Sinks;

@Service
public class OpenRouteService {
    private static final Logger logger = LogManager.getLogger(LogService.class);
    @Getter
    private final Sinks.Many<Boolean> loadingSink = Sinks.many().replay().latest();
    private static final String GEOCODE_URL = "https://api.openrouteservice.org/geocode/";
    private final WebClient webClient;

    public OpenRouteService() {
        this.webClient = WebClient.builder().baseUrl(GEOCODE_URL).build();
    }


}

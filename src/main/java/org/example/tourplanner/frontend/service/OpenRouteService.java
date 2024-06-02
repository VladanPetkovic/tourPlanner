package org.example.tourplanner.frontend.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class OpenRouteService {
    private static final Logger logger = LogManager.getLogger(OpenRouteService.class);
    private static final String GEOCODE_URL = "https://api.openrouteservice.org/geocode/";
    private static final String TILE_URL_TEMPLATE = "https://tile.openstreetmap.org/{zoom}/{x}/{y}.png";
    /** LEAVING THIS KEY ON PURPOSE, SO THE PROJECT CAN BE TESTED, WITHOUT SETTING UP A NEW KEY **/
    private static final String API_KEY = "5b3ce3597851110001cf62484145947120374713905633154bb7acc3";
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenRouteService() {
        this.webClient = WebClient.builder().baseUrl(GEOCODE_URL).build();
    }

    public Mono<JsonNode> searchAddress(String address) {
        String url = "search";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("api_key", API_KEY)
                        .queryParam("text", address)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .<JsonNode>handle((response, sink) -> {
                    try {
                        sink.next(objectMapper.readTree(response));
                    } catch (Exception e) {
                        sink.error(new RuntimeException("Failed to parse JSON response", e));
                    }
                })
                .doOnSuccess(response -> {
                    logger.info("Search for address: {} completed successfully", address);
                })
                .doOnError(error -> {
                    logger.error("Error occurred while searching for address: {}", address, error);
                });
    }

    public Mono<BufferedImage> fetchTile(int zoom, int x, int y) {
        return webClient.get()
                .uri(TILE_URL_TEMPLATE, zoom, x, y)
                .header("User-Agent", "CarSharingTest/1.0 Demo application for the SAM-course")
                .retrieve()
                .bodyToMono(byte[].class)
                .map(byteArray -> {
                    try (InputStream inputStream = new ByteArrayInputStream(byteArray)) {
                        return ImageIO.read(inputStream);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to read image from InputStream", e);
                    }
                })
                .doOnSuccess(image -> {
                    logger.info("Tile fetch for zoom: {}, x: {}, y: {} completed successfully", zoom, x, y);
                })
                .doOnError(error -> {
                    logger.error("Error occurred while fetching tile for zoom: {}, x: {}, y: {}", zoom, x, y, error);
                });
    }
}

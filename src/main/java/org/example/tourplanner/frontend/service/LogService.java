package org.example.tourplanner.frontend.service;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.tourplanner.frontend.model.Log;
import org.example.tourplanner.frontend.model.TourAverage;
import org.example.tourplanner.frontend.model.TourPopularity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Service
public class LogService {
    private static final Logger logger = LogManager.getLogger(LogService.class);
    @Getter
    private final Sinks.Many<Boolean> loadingSink = Sinks.many().replay().latest();
    private static final String BASE_URL = "http://localhost:8080/logs";

    private final WebClient webClient;

    public LogService() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    public Mono<Log> createLog(Log log) {
        logger.info("Creating log for tour ID: {}", log.getTour().getTourid());
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.queryParam("tourId", log.getTour().getTourid()).build())
                .bodyValue(log)
                .retrieve()
                .bodyToMono(Log.class)
                .doOnSuccess(createdLog -> logger.info("Log created successfully with ID: {}", createdLog.getLog_id()))
                .doOnError(error -> logger.error("Failed to create log", error));
    }

    public Mono<Log[]> getLogs() {
        logger.info("Fetching all logs");
        return webClient.get()
                .retrieve()
                .bodyToMono(Log[].class)
                .doOnSuccess(logs -> logger.info("Fetched {} logs", logs.length))
                .doOnError(error -> logger.error("Failed to fetch logs", error));
    }

    public Mono<Log[]> getLogs(String searchString, Long tourId) {
        logger.info("Searching for logs with string: {}", searchString);
        return webClient.get()
                .uri("/search?comment=" + searchString + "&tour_id=" + tourId)
                .retrieve()
                .bodyToMono(Log[].class)
                .flatMap(logs -> simulateLatency().then(Mono.just(logs)))
                .doOnSuccess(logs -> {
                    loadingSink.tryEmitNext(false);
                    logger.info("Fetched {} logs", logs.length);
                })
                .doOnError(error -> {
                    loadingSink.tryEmitNext(false);
                    logger.error("Failed to fetch logs", error);
                });
    }

    public Mono<Log[]> getLogsByTourId(Long tourId) {
        logger.info("Fetching logs for tour ID: {}", tourId);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/logsByTour").queryParam("tour_id", tourId).build())
                .retrieve()
                .bodyToMono(Log[].class)
                .doOnSuccess(logs -> logger.info("Fetched {} logs for tour ID {}", logs.length, tourId))
                .doOnError(error -> logger.error("Failed to fetch logs for tour ID: {}", tourId, error));
    }

    public Mono<Log> getLog(long id) {
        logger.info("Fetching log by ID: {}", id);
        return webClient.get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Log.class)
                .doOnSuccess(log -> logger.info("Fetched log with ID: {}", log.getLog_id()))
                .doOnError(error -> logger.error("Failed to fetch log by ID: {}", id, error));
    }

    public Mono<Log> updateLog(Log log) {
        logger.info("Updating log with ID: {}", log.getLog_id());
        return webClient.put()
                .uri("/" + log.getLog_id())
                .bodyValue(log)
                .retrieve()
                .bodyToMono(Log.class)
                .doOnSuccess(updatedLog -> logger.info("Log updated successfully for ID: {}", updatedLog.getLog_id()))
                .doOnError(error -> logger.error("Failed to update log with ID: {}", log.getLog_id(), error));
    }

    public Mono<Void> deleteLog(long id) {
        logger.info("Deleting log with ID: {}", id);
        return webClient.delete()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(aVoid -> logger.info("Log deleted successfully ID: {}", id))
                .doOnError(error -> logger.error("Failed to delete log with ID: {}", id, error));
    }

    public Mono<TourAverage> getAverages(long tourId) {
        logger.info("Fetching averages for tour ID: {}", tourId);
        return webClient.get()
                .uri("/averages?tourId=" + tourId)
                .retrieve()
                .bodyToMono(TourAverage.class)
                .doOnSuccess(averages -> logger.info("Fetched averages for tour ID: {}", tourId))
                .doOnError(error -> logger.error("Failed to fetch averages for tour ID: {}", tourId, error));
    }

    public Mono<TourAverage[]> getAverages() {
        logger.info("Fetching averages for all tours.");
        return webClient.get()
                .uri("/averagesAll")
                .retrieve()
                .bodyToMono(TourAverage[].class)
                .doOnSuccess(averages -> logger.info("Fetched averages for all tours."))
                .doOnError(error -> logger.error("Failed to fetch averages for all tours.", error));
    }

    public Mono<Double> getAverageRating(long tourId) {
        logger.info("Fetching average rating for tour ID: {}", tourId);
        return webClient.get()
                .uri("/average-total-rating?tourId=" + tourId)
                .retrieve()
                .bodyToMono(Double.class)
                .doOnSuccess(count -> logger.info("Fetched average rating for tour ID: {}", tourId))
                .doOnError(error -> logger.error("Failed to fetch average rating for tour ID: {}", tourId, error));
    }

    public Mono<TourPopularity> getTourPopularity(long tourId) {
        logger.info("Fetching popularity for tour ID: {}", tourId);
        return webClient.get()
                .uri("/popularity?tourId=" + tourId)
                .retrieve()
                .bodyToMono(TourPopularity.class)
                .doOnSuccess(count -> logger.info("Fetched popularity for tour ID: {}", tourId))
                .doOnError(error -> logger.error("Failed to fetch popularity for tour ID: {}", tourId, error));
    }

    public Mono<Long> countLogsByTourId(long tourId) {
        logger.info("Fetching log count for tour ID: {}", tourId);
        return webClient.get()
                .uri("/count?tourId=" + tourId)
                .retrieve()
                .bodyToMono(Long.class)
                .doOnSuccess(count -> logger.info("Fetched log count for tour ID: {}", tourId))
                .doOnError(error -> logger.error("Failed to fetch log count for tour ID: {}", tourId, error));
    }

    private Mono<Void> simulateLatency() {
        return Mono.delay(Duration.ofSeconds(0))
                .then()
                .doOnSubscribe(subscription -> logger.info("Simulating latency..."))
                .doOnTerminate(() -> logger.info("Latency simulation completed"));
    }

}

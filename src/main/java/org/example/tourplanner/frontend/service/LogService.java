package org.example.tourplanner.frontend.service;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.tourplanner.frontend.model.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LogService {
    private static final Logger logger = LogManager.getLogger(LogService.class);
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
}

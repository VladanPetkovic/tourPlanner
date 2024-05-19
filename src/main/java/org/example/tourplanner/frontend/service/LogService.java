package org.example.tourplanner.frontend.service;

import org.example.tourplanner.frontend.model.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LogService {
    private static final String BASE_URL = "http://localhost:8080/logs";

    private final WebClient webClient;

    public LogService() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    public Mono<Log> createLog(Log log) {
        return webClient.post()
                .bodyValue(log)
                .retrieve()
                .bodyToMono(Log.class);
    }

    public Mono<Log[]> getLogs() {
        return webClient.get()
                .retrieve()
                .bodyToMono(Log[].class);
    }

    public Mono<Log> getLog(long id) {
        return webClient.get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Log.class);
    }

    public Mono<Log> updateLog(Log log) {
        return webClient.put()
                .uri("/" + log.getLog_id())
                .bodyValue(log)
                .retrieve()
                .bodyToMono(Log.class);
    }

    public Mono<Void> deleteLog(long id) {
        return webClient.delete()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}

package com.assignment.sensorservice.scheduler;


import com.assignment.sensorservice.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelemetryScheduler {

    private final SensorService sensorService;
    @Value("${telemetry.scheduler.api-down-log-interval:1m}")
    private Duration apiDownLogInterval;
    private Instant lastApiDownLogAt;
    private boolean apiDown;

    @Scheduled(fixedRate = 10000)
    public void fetchAndPushData() {
        sensorService.getLatestSafe()
                .switchIfEmpty(Mono.fromRunnable(this::logApiDown)
                        .then(Mono.empty()))
                .subscribe(telemetry -> {
                    if (apiDown) {
                        log.info("External IoT API recovered; telemetry collection resumed");
                        apiDown = false;
                        lastApiDownLogAt = null;
                    }
                    log.info("Fetched Telemetry: {}", telemetry);
                    sensorService.pushToAutomation(telemetry)
                            .doOnError(err -> log.error("Failed to push telemetry to automation: {}", err.getMessage()))
                            .onErrorResume(err -> Mono.empty())
                            .subscribe();
                },
                error -> log.error("Unexpected error in scheduler: {}", error.getMessage()));
    }

    private void logApiDown() {
        Instant now = Instant.now();
        if (!apiDown || lastApiDownLogAt == null || Duration.between(lastApiDownLogAt, now).compareTo(apiDownLogInterval) >= 0) {
            log.warn("External IoT API unavailable; skipping scheduled collection. Next retries continue every 10 seconds.");
            apiDown = true;
            lastApiDownLogAt = now;
        }
    }
}
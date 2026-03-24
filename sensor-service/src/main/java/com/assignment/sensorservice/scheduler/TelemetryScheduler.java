package com.assignment.sensorservice.scheduler;


import com.assignment.sensorservice.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelemetryScheduler {

    private final SensorService sensorService;

    @Scheduled(fixedRate = 10000) // every 10 seconds
    public void fetchAndPushData() {
        sensorService.getLatest()
                .subscribe(
                        telemetry -> {
                            log.info("Fetched Telemetry: {}", telemetry);
                            sensorService.pushToAutomation(telemetry).subscribe();
                        },
                        err -> log.error("Error in scheduler: {}", err.getMessage())
                );
    }
}
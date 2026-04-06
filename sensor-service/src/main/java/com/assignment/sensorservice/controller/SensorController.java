package com.assignment.sensorservice.controller;

import com.assignment.sensorservice.dto.TelemetryResponse;
import com.assignment.sensorservice.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @GetMapping("/latest")
    public Mono<ResponseEntity<TelemetryResponse>> getLatest() {
        return sensorService.getLatestWithFallback()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
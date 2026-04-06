package com.assignment.sensorservice.service;

import com.assignment.sensorservice.client.ExternalIoTClient;
import com.assignment.sensorservice.client.ExternalIoTClient.Device;
import com.assignment.sensorservice.dto.TelemetryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final ExternalIoTClient externalIoTClient;

    @Value("${external-iot.username}")
    private String username;

    @Value("${external-iot.password}")
    private String password;

    private String deviceId; // dynamically stored
    private String token;    // JWT token
    private final AtomicReference<TelemetryResponse> lastKnownTelemetry = new AtomicReference<>();

    // --- Get latest telemetry ---
    public Mono<TelemetryResponse> getLatest() {
        return ensureAuthenticated()
                .flatMap(t -> ensureDeviceId())
                .flatMap(id -> externalIoTClient.getTelemetry(id, token))
                .doOnNext(lastKnownTelemetry::set);
    }

    // Graceful path for scheduler/controller to avoid propagating upstream timeouts as 500 errors.
    public Mono<TelemetryResponse> getLatestSafe() {
        return getLatest()
                .onErrorResume(err -> Mono.empty());
    }

    public Mono<TelemetryResponse> getLatestWithFallback() {
        return getLatestSafe()
                .switchIfEmpty(Mono.defer(() -> Mono.justOrEmpty(lastKnownTelemetry.get())));
    }

    // --- Push telemetry to Automation Service (placeholder) ---
    public Mono<Void> pushToAutomation(TelemetryResponse telemetry) {
        // call automation service via WebClient/OpenFeign
        return Mono.empty();
    }

    // --- Ensure token ---
    private Mono<String> ensureAuthenticated() {
        if (token != null) return Mono.just(token);

        return externalIoTClient.authenticate(username, password)
                .map(auth -> {
                    this.token = auth.getAccessToken();
                    return this.token;
                });
    }

    // --- Ensure deviceId ---
    private Mono<String> ensureDeviceId() {
        if (deviceId != null) return Mono.just(deviceId);

        return externalIoTClient.getDevices(token)
                .map(devices -> {
                    Device first = devices.get(0); // pick first device
                    this.deviceId = first.getDeviceId();
                    return deviceId;
                });
    }
}
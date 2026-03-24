package com.assignment.sensorservice.client;

import com.assignment.sensorservice.dto.TelemetryResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ExternalIoTClient {

    private final WebClient webClient;

    public ExternalIoTClient(@Value("${external-iot.base-url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    // --- Authentication ---
    public Mono<AuthResponse> authenticate(String username, String password) {
        return webClient.post()
                .uri("/auth/login")
                .bodyValue(new AuthRequest(username, password))
                .retrieve()
                .bodyToMono(AuthResponse.class);
    }

    // --- Get all devices ---
    public Mono<List<Device>> getDevices(String token) {
        return webClient.get()
                .uri("/devices")
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToFlux(Device.class)
                .collectList();
    }

    // --- Get telemetry for device ---
    public Mono<TelemetryResponse> getTelemetry(String deviceId, String token) {
        return webClient.get()
                .uri("/devices/telemetry/" + deviceId)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(TelemetryResponse.class);
    }

    // --- DTOs ---
    @Data
    public static class AuthRequest {
        private String username;
        private String password;

        public AuthRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Data
    public static class AuthResponse {
        private String accessToken;
        private String refreshToken;
    }

    @Data
    public static class Device {
        private String deviceId;
        private String name;
        private String zoneId;
    }
}
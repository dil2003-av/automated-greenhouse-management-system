package com.assignment.zoneservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");

            // Call IoT API to get token safely
            String token = fetchIoTToken();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }

    private String fetchIoTToken() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String loginUrl = "http://104.211.95.241:8080/api/auth/login";

            Map<String, String> body = Map.of(
                    "username", "username",
                    "password", "12345"
            );

            ResponseEntity<Map> response = restTemplate.postForEntity(loginUrl, body, Map.class);
            return (String) response.getBody().get("accessToken");
        } catch (Exception e) {
            System.out.println("WARNING: Failed to fetch IoT token: " + e.getMessage());
            return null; // fallback to null, zone will still save
        }
    }
}
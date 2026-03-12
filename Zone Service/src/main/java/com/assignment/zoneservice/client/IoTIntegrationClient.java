package com.assignment.zoneservice.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "iotClient", url = "http://104.211.95.241:8080", configuration = com.assignment.zoneservice.config.FeignConfig.class)
public interface IoTIntegrationClient {

    @PostMapping("/api/devices/register")
    default Map<String, Object> registerDevice(Map<String, Object> request) {
        try {
            return registerDeviceInternal(request);
        } catch (FeignException e) {
            System.out.println("WARNING: IoT device registration failed: " + e.getMessage());
            // Return fallback deviceId
            return Map.of("deviceId", "fallback-device-" + System.currentTimeMillis());
        }
    }

    @PostMapping("/api/devices/register")
    Map<String, Object> registerDeviceInternal(@RequestBody Map<String, Object> request);
}
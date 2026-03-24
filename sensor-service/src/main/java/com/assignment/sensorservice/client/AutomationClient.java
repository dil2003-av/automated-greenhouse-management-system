package com.assignment.sensorservice.client;

import com.assignment.sensorservice.dto.SensorDataDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AutomationClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendToAutomation(SensorDataDTO data) {
        restTemplate.postForObject(
                "http://localhost:8083/api/automation/process",
                data,
                String.class
        );
    }
}
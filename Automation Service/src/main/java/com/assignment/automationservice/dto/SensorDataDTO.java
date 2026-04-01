package com.assignment.automationservice.dto;

import lombok.Data;

@Data
public class SensorDataDTO {
    private String deviceId;
    private String zoneId;
    private double temperature;
    private double humidity;
}
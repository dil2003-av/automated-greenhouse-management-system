package com.assignment.sensorservice.dto;

import lombok.Data;

@Data
public class TelemetryResponse {

    private String deviceId;
    private String zoneId;
    private Value value;
    private String capturedAt;

    @Data
    public static class Value {
        private double temperature;
        private String tempUnit;
        private double humidity;
        private String humidityUnit;
    }
}
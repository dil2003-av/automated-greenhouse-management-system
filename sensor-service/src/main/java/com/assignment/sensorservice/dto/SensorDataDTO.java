package com.assignment.sensorservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SensorDataDTO {
    private String deviceId;
    private String zoneId;
    private double temperature;
    private double humidity;
    private Date CapturedAt;
}
package com.assignment.automationservice.dto;

import lombok.Data;

@Data
public class ZoneDTO {

    private String id;
    private String name;
    private Double minTemp;
    private Double maxTemp;
    private String deviceId;
}
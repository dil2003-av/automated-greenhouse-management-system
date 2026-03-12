package com.assignment.zoneservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ZoneResponseDTO {

    private String id;
    private String name;
    private Double minTemp;
    private Double maxTemp;
    private String deviceId;
}
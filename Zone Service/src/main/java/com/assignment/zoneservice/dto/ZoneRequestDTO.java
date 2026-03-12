package com.assignment.zoneservice.dto;

import lombok.Data;

@Data
public class ZoneRequestDTO {

    private String name;
    private Double minTemp;
    private Double maxTemp;
}
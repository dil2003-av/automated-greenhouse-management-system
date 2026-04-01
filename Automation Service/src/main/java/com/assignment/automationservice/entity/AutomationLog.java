package com.assignment.automationservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "automation_logs")
public class AutomationLog {

    @Id
    private String id;

    private String zoneId;
    private double temperature;
    private String action;
    private Date timestamp;
}
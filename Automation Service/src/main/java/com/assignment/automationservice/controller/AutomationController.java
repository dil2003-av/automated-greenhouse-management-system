package com.assignment.automationservice.controller;

import com.assignment.automationservice.dto.SensorDataDTO;

import com.assignment.automationservice.entity.AutomationLog;
import com.assignment.automationservice.repository.AutomationLogRepository;
import com.assignment.automationservice.service.AutomationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation")
@RequiredArgsConstructor
public class AutomationController {

    private final AutomationService service;
    private final AutomationLogRepository repository;

    @PostMapping("/process")
    public void process(@RequestBody SensorDataDTO data) {
        service.processData(data);
    }

    @GetMapping("/logs")
    public List<AutomationLog> getLogs() {
        return repository.findAll();
    }
}
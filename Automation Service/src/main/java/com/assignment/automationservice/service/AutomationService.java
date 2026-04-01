package com.assignment.automationservice.service;

import com.assignment.automationservice.client.ZoneClient;
import com.assignment.automationservice.dto.SensorDataDTO;
import com.assignment.automationservice.dto.ZoneDTO;
import com.assignment.automationservice.entity.AutomationLog;
import com.assignment.automationservice.repository.AutomationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AutomationService {

    private final ZoneClient zoneClient;
    private final AutomationLogRepository repository;

    public void processData(SensorDataDTO data) {

        ZoneDTO zone = zoneClient.getZone(data.getZoneId());

        double temp = data.getTemperature();
        String action = null;

        if (temp > zone.getMaxTemp()) {
            action = "TURN_FAN_ON";
        } else if (temp < zone.getMinTemp()) {
            action = "TURN_HEATER_ON";
        }

        if (action != null) {
            AutomationLog log = new AutomationLog();
            log.setZoneId(data.getZoneId());
            log.setTemperature(temp);
            log.setAction(action);
            log.setTimestamp(new Date());

            repository.save(log);
        }
    }
}
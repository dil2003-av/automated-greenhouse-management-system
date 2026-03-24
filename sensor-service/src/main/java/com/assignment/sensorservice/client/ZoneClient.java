package com.assignment.sensorservice.client;

import com.assignment.sensorservice.dto.ZoneDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "zone-service")
public interface ZoneClient {
    @GetMapping("/api/zones")
    List<ZoneDTO> getAllZones();
}

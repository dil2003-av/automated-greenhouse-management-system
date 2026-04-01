package com.assignment.automationservice.client;

import com.assignment.automationservice.dto.ZoneDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zone-service")
public interface ZoneClient {

    @GetMapping("/api/zones/{id}")
    ZoneDTO getZone(@PathVariable("id") String id);
}
package com.assignment.zoneservice.controller;

import com.assignment.zoneservice.dto.ZoneRequestDTO;
import com.assignment.zoneservice.dto.ZoneResponseDTO;
import com.assignment.zoneservice.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping
    public ZoneResponseDTO createZone(@RequestBody ZoneRequestDTO request) {
        return zoneService.createZone(request);
    }

    @GetMapping("/{id}")
    public ZoneResponseDTO getZone(@PathVariable String id) {
        return zoneService.getZone(id);
    }

    @GetMapping
    public List<ZoneResponseDTO> getAllZones() {
        return zoneService.getAllZones();
    }

    @PutMapping("/{id}")
    public ZoneResponseDTO updateZone(@PathVariable String id,
                                      @RequestBody ZoneRequestDTO request) {
        return zoneService.updateZone(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable String id) {
        zoneService.deleteZone(id);
    }
}

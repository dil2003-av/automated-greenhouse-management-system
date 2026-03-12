package com.assignment.zoneservice.service.Impl;

import com.assignment.zoneservice.client.IoTIntegrationClient;
import com.assignment.zoneservice.dto.ZoneRequestDTO;
import com.assignment.zoneservice.dto.ZoneResponseDTO;
import com.assignment.zoneservice.model.Zone;
import com.assignment.zoneservice.repository.ZoneRepository;
import com.assignment.zoneservice.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final IoTIntegrationClient iotClient;

    @Override
    public ZoneResponseDTO createZone(ZoneRequestDTO request) {

        if (request.getMinTemp() >= request.getMaxTemp()) {
            throw new RuntimeException("minTemp must be less than maxTemp");
        }

        String deviceId = null;
        try {
            Map<String, Object> deviceRequest = new HashMap<>();
            deviceRequest.put("name", request.getName() + "-sensor");
            deviceRequest.put("zoneId", request.getName());

            Map<String, Object> response = iotClient.registerDevice(deviceRequest);
            deviceId = (String) response.get("deviceId");

        } catch (Exception e) {
            System.out.println("WARNING: IoT device registration failed, using fallback deviceId: " + e.getMessage());
            deviceId = "fallback-device-" + System.currentTimeMillis();
        }

        Zone zone = Zone.builder()
                .name(request.getName())
                .minTemp(request.getMinTemp())
                .maxTemp(request.getMaxTemp())
                .deviceId(deviceId)
                .build();

        zoneRepository.save(zone);

        return ZoneResponseDTO.builder()
                .id(zone.getId())
                .name(zone.getName())
                .minTemp(zone.getMinTemp())
                .maxTemp(zone.getMaxTemp())
                .deviceId(zone.getDeviceId())
                .build();
    }

    @Override
    public ZoneResponseDTO getZone(String id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));
        return mapToDTO(zone);
    }

    @Override
    public List<ZoneResponseDTO> getAllZones() {
        return zoneRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public ZoneResponseDTO updateZone(String id, ZoneRequestDTO request) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        zone.setName(request.getName());
        zone.setMinTemp(request.getMinTemp());
        zone.setMaxTemp(request.getMaxTemp());

        zoneRepository.save(zone);
        return mapToDTO(zone);
    }

    @Override
    public void deleteZone(String id) {
        zoneRepository.deleteById(id);
    }

    private ZoneResponseDTO mapToDTO(Zone zone) {
        return ZoneResponseDTO.builder()
                .id(zone.getId())
                .name(zone.getName())
                .minTemp(zone.getMinTemp())
                .maxTemp(zone.getMaxTemp())
                .deviceId(zone.getDeviceId())
                .build();
    }
}
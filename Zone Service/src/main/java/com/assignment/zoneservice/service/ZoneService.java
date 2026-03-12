package com.assignment.zoneservice.service;



import com.assignment.zoneservice.dto.ZoneRequestDTO;
import com.assignment.zoneservice.dto.ZoneResponseDTO;

import java.util.List;

public interface ZoneService {

    ZoneResponseDTO createZone(ZoneRequestDTO request);

    ZoneResponseDTO getZone(String id);

    List<ZoneResponseDTO> getAllZones();

    ZoneResponseDTO updateZone(String id, ZoneRequestDTO request);

    void deleteZone(String id);
}
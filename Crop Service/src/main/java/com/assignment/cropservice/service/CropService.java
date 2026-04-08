package com.assignment.cropservice.service;

import com.assignment.cropservice.dto.CropRequestDTO;
import com.assignment.cropservice.entity.Crop;
import com.assignment.cropservice.Enum.CropStatus;
import com.assignment.cropservice.repository.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropService {

    private final CropRepository repository;

    public Crop createCrop(CropRequestDTO dto) {
        Crop crop = Crop.builder()
                .name(dto.getName())
                .zoneId(dto.getZoneId())
                .status(CropStatus.SEEDLING)
                .build();

        return repository.save(crop);
    }

    public Crop updateStatus(String id, CropStatus status) {
        Crop crop = repository.findById(id).orElseThrow();
        crop.setStatus(status);
        return repository.save(crop);
    }

    public List<Crop> getAll() {
        return repository.findAll();
    }
}
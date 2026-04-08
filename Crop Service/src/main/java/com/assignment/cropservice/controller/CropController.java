package com.assignment.cropservice.controller;

import com.assignment.cropservice.dto.CropRequestDTO;
import com.assignment.cropservice.entity.Crop;
import com.assignment.cropservice.Enum.CropStatus;
import com.assignment.cropservice.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {

    private final CropService service;

    @PostMapping
    public Crop create(@RequestBody CropRequestDTO dto) {
        return service.createCrop(dto);
    }

    @PutMapping("/{id}/status")
    public Crop updateStatus(@PathVariable String id,
                             @RequestParam CropStatus status) {
        return service.updateStatus(id, status);
    }

    @GetMapping
    public List<Crop> getAll() {
        return service.getAll();
    }
}
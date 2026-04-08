package com.assignment.cropservice.repository;

import com.assignment.cropservice.entity.Crop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CropRepository extends MongoRepository<Crop, String> {
}
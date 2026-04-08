package com.assignment.cropservice.entity;

import com.assignment.cropservice.Enum.CropStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "crops")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crop {

    @Id
    private String id;

    private String name;

    private String zoneId;

    private CropStatus status;
}
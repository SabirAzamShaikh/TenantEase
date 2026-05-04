package com.example.TenantEase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponseDTO {
    private Long id;
    private String roomNumber;
    private String description;
    private String roomType;
    private double rentAmount;
    private double depositAmount;
    private boolean available;
    private String furnishingType;
    private int occupancyLimit;
    private int floor;
    private boolean attachedBath;
    private String amenities;
    private List<String> imageUrls;
    private Long propertyId;
}

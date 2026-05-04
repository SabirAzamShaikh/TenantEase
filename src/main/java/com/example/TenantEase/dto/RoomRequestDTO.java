package com.example.TenantEase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequestDTO {
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
    private List<MultipartFile> roomImages; // List of images for the room
}

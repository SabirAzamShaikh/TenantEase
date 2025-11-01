package com.example.TenantEase.dto;

import com.example.TenantEase.enums.PropertyType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Accessors(chain = true)
public class PropertyRequestDto {
    private String name; // Property name (e.g., "Sai Residency")

    private PropertyType type; // Type: HOUSE, FLAT, SHOP

    private String address; // Full property address
    private final int totalRooms;//total number of room there in an apartment;
    private final int totalFloors;//total number of Floors there in an apartment;
    private final List<MultipartFile> propertyImages;
}

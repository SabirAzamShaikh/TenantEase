package com.example.TenantEase.dto;

import com.example.TenantEase.enums.PropertyType;
import com.example.TenantEase.model.Room;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PropertyResponseDto {
    private Long propertyId; // Unique property ID


    private String name; // Property name (e.g., "Sai Residency")


    private PropertyType type; // Type: HOUSE, FLAT, SHOP

    private String address; // Full property address

    private int totalRooms; // Total rooms in this property

    private String ownerName;//owner to who's the Property Belongs

    private  int totalFloors; //Total floors in this property

    private List<Room> rooms; // List of rooms

    private List<String> propertyImagePath;
}

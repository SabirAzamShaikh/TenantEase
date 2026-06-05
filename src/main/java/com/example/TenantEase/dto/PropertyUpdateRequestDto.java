package com.example.TenantEase.dto;

import com.example.TenantEase.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyUpdateRequestDto {
    private Long propertyId;
    private String propertyName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private Integer totalRooms;
    private PropertyType propertyType;
    private Double rentAmount;
    private String description;
    // Image can be handled via multipart if needed
}

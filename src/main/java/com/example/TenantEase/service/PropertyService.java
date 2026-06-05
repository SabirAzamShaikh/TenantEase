package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.PropertyRequestDto;
import com.example.TenantEase.dto.PropertyResponseDto;
import com.example.TenantEase.dto.PropertyUpdateRequestDto;
import com.example.TenantEase.enums.ResourceType;
import com.example.TenantEase.util.CheckPlanLimit;

import java.util.List;

public interface PropertyService {

    Message<PropertyResponseDto> addProperty(PropertyRequestDto property);

    @CheckPlanLimit(resource = ResourceType.PROPERTY)
    Message<PropertyResponseDto> updateProperty(PropertyUpdateRequestDto propertyUpdateDto);

    Message<PropertyResponseDto> getPropertyById(Long propertyId);
    Message<List<PropertyResponseDto>> getAllProperty(int page, int size);
    Message<String> deleteProperty(Long propertyId);

    Message<List<PropertyResponseDto>> getPropertyByOwner(String ownerName);
}

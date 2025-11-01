package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.PropertyRequestDto;
import com.example.TenantEase.dto.PropertyResponseDto;

import java.util.List;

public interface PropertyService {

    Message<PropertyResponseDto> addProperty(PropertyRequestDto property);
    Message<PropertyResponseDto> getPropertyById(Long propertyId);
    Message<List<PropertyResponseDto>> getAllProperty(int page, int size);
    Message<String> deleteProperty(Long propertyId);

}

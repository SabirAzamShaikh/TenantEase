package com.example.TenantEase.mapper;

import com.example.TenantEase.dto.PropertyRequestDto;
import com.example.TenantEase.dto.PropertyResponseDto;
import com.example.TenantEase.model.Property;

public interface PropertyMapper {

    Property requestToEntity(PropertyRequestDto requestDto);

    PropertyResponseDto entityToResponseDto(Property property);
}

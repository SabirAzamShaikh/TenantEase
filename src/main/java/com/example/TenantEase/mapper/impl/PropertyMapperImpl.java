package com.example.TenantEase.mapper.impl;

import com.example.TenantEase.dto.PropertyRequestDto;
import com.example.TenantEase.dto.PropertyResponseDto;
import com.example.TenantEase.mapper.PropertyMapper;
import com.example.TenantEase.model.Property;
import org.springframework.stereotype.Component;

@Component
public class PropertyMapperImpl implements PropertyMapper {
@Override
public Property requestToEntity(PropertyRequestDto requestDto) {
        return new Property().setName(requestDto.getName()).setType(requestDto.getType())
                .setAddress(requestDto.getAddress()).setTotalRooms(requestDto.getTotalRooms()).setTotalFloors(requestDto.getTotalFloors());
    }
@Override
public PropertyResponseDto entityToResponseDto(Property property) {
        return new PropertyResponseDto().setPropertyId(property.getPropertyId()).setOwnerName(property.getOwnerName())
                .setRooms(property.getRooms()).setTotalRooms(property.getTotalRooms()).setName(property.getName())
                .setType(property.getType()).setAddress(property.getAddress()).setTotalFloors(property.getTotalFloors()).setPropertyImagePath(property.getPropertyImagePath());
    }
}

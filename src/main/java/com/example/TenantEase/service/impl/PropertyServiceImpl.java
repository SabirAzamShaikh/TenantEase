package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.PropertyRepository;
import com.example.TenantEase.Repository.UserRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.PropertyRequestDto;
import com.example.TenantEase.dto.PropertyResponseDto;
import com.example.TenantEase.jwt.JwtUtil;
import com.example.TenantEase.mapper.PropertyMapper;
import com.example.TenantEase.model.Property;
import com.example.TenantEase.model.User;
import com.example.TenantEase.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final JwtUtil util;
    private final UserRepository userRepository;
    private final PropertyMapper propertyMapper;

    @Override
    public Message<PropertyResponseDto> addProperty(PropertyRequestDto requestDto) {
        Message<PropertyResponseDto> message = new Message<>();
        try {
            String token = util.extractTokenFromRequest();
            String username = token != null ? util.extractUsername(token) : null;
            User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User Not Found with Username " + username));
            Property property = propertyMapper.requestToEntity(requestDto);
            property.setOwnerName(username);
            Property savedProperty = propertyRepository.save(property);
            message.setResponseMessage("Property saved successfully");
            message.setStatus(HttpStatus.CREATED);
            message.setData(propertyMapper.entityToResponseDto(savedProperty));
        } catch (Exception e) {
            throw new RuntimeException("Failed to save property", e);
        }
        return message;
    }

    @Override
    public Message<PropertyResponseDto> getPropertyById(Long propertyId) {
        Message<PropertyResponseDto> message = new Message<>();
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found with id: " + propertyId));
        message.setResponseMessage("Property fetched successfully");
        message.setStatus(HttpStatus.OK);
        message.setData(propertyMapper.entityToResponseDto(property));
        return message;
    }

    @Override
    public Message<List<PropertyResponseDto>> getAllProperty(int page, int size) {
        Message<List<PropertyResponseDto>> message = new Message<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<Property> properties = propertyRepository.findAll(pageable);
        message.setResponseMessage("Properties fetched successfully");
        message.setStatus(HttpStatus.OK);
        message.setData(properties.getContent().stream().map(propertyMapper::entityToResponseDto).toList());
        return message;
    }

    @Override
    public Message<String> deleteProperty(Long propertyId) {
        Message<String> message = new Message<>();
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found with id: " + propertyId));
        propertyRepository.delete(property);
        message.setResponseMessage("Property deleted successfully");
        message.setStatus(HttpStatus.OK);
        message.setData("Deleted Property ID: " + propertyId);
        return message;
    }
}



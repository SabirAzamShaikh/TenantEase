package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.ImageDataRepository;
import com.example.TenantEase.Repository.PropertyRepository;
import com.example.TenantEase.Repository.UserRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.PropertyRequestDto;
import com.example.TenantEase.dto.PropertyResponseDto;
import com.example.TenantEase.dto.PropertyUpdateRequestDto;
import com.example.TenantEase.enums.ResourceType;
import com.example.TenantEase.exception.ResourceNotFoundException;
import com.example.TenantEase.jwt.JwtUtil;
import com.example.TenantEase.mapper.PropertyMapper;
import com.example.TenantEase.model.Property;
import com.example.TenantEase.model.User;
import com.example.TenantEase.service.PlanUsageService;
import com.example.TenantEase.service.PropertyService;
import com.example.TenantEase.util.CheckPlanLimit;
import com.example.TenantEase.util.UtilHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final JwtUtil util;
    private final UserRepository userRepository;
    private final PropertyMapper propertyMapper;
    private final ImageDataRepository imageRepository;
    private final UtilHelper utility;
    private final PlanUsageService planUsageService;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB per file

    @Override
    @CheckPlanLimit(resource = ResourceType.PROPERTY)
    public Message<PropertyResponseDto> addProperty(PropertyRequestDto requestDto) {
        Message<PropertyResponseDto> message = new Message<>();
        try {
            String uuid;
            List<String> imagePath = new ArrayList<>();
            String token = util.extractTokenFromRequest();
            if (token == null) {
                message.setResponseMessage("Authorization token not found");
                message.setStatus(HttpStatus.UNAUTHORIZED);
                return message;
            }

            String username = util.extractUsername(token);
            if (username == null) {
                message.setResponseMessage("Invalid token - username cannot be extracted");
                message.setStatus(HttpStatus.UNAUTHORIZED);
                return message;
            }

            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User Not Found with Email: " + username));
           List<String> ImagePaths= utility.imageSaver(requestDto.getPropertyImages());

            Property property = propertyMapper.requestToEntity(requestDto);
            property.setOwnerName(username).setPropertyImagePath(ImagePaths);
            Property savedProperty = propertyRepository.save(property);
            planUsageService.incrementUsage(user.getUserId(), ResourceType.PROPERTY);
            message.setResponseMessage("Property saved successfully");
            message.setStatus(HttpStatus.CREATED);
            message.setData(propertyMapper.entityToResponseDto(savedProperty));
        } catch (RuntimeException e) {
            message.setResponseMessage("Error: " + e.getMessage());
            message.setStatus(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            message.setResponseMessage("Failed to save property: " + e.getMessage());
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return message;
    }

    @CheckPlanLimit(resource = ResourceType.PROPERTY)
    @Override
    public Message<PropertyResponseDto> updateProperty(PropertyUpdateRequestDto propertyUpdateDto) {
        log.info("Updating property with ID: {}", propertyUpdateDto.getPropertyId());
        Message<PropertyResponseDto> message = new Message<>();

        try {
            // Fetch existing property
            Property existingProperty = propertyRepository.findById(propertyUpdateDto.getPropertyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + propertyUpdateDto.getPropertyId()));

            // Update fields if provided
            if (propertyUpdateDto.getPropertyName() != null) {
                existingProperty.setName(propertyUpdateDto.getPropertyName());
            }
            if (propertyUpdateDto.getAddress() != null) {
                existingProperty.setAddress(propertyUpdateDto.getAddress());
            }
//            if (propertyUpdateDto.getCity() != null) {
//                existingProperty.setcCity(propertyUpdateDto.getCity());
//            }
//            if (propertyUpdateDto.getState() != null) {
//                existingProperty.setState(propertyUpdateDto.getState());
//            }
//            if (propertyUpdateDto.getZipCode() != null) {
//                existingProperty.setZipCode(propertyUpdateDto.getZipCode());
//            }
            if (propertyUpdateDto.getTotalRooms() != null) {
                existingProperty.setTotalRooms(propertyUpdateDto.getTotalRooms());
            }
            if (propertyUpdateDto.getPropertyType() != null) {
                existingProperty.setType(propertyUpdateDto.getPropertyType());
            }


            // Save updated property
            Property updatedProperty = propertyRepository.save(existingProperty);

            message.setResponseMessage("Property updated successfully");
            message.setStatus(HttpStatus.OK);
            message.setData(propertyMapper.entityToResponseDto(updatedProperty));

        } catch (ResourceNotFoundException e) {
            log.error("Property not found: ", e);
            message.setResponseMessage(e.getMessage());
            message.setStatus(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error updating property: ", e);
            message.setResponseMessage("Failed to update property: " + e.getMessage());
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
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
        
        User user = userRepository.findByEmail(property.getOwnerName())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        propertyRepository.delete(property);
        planUsageService.decrementUsage(user.getUserId(), ResourceType.PROPERTY);
        message.setResponseMessage("Property deleted successfully");
        message.setStatus(HttpStatus.OK);
        message.setData("Deleted Property ID: " + propertyId);
        return message;
    }

    @Override
    public Message<List<PropertyResponseDto>> getPropertyByOwner(String ownerName) {
Message<List<PropertyResponseDto>> message=new Message<>();

        List<PropertyResponseDto> byOwnerName = propertyRepository.findByOwnerName(ownerName).stream().map(propertyMapper::entityToResponseDto).toList();
       message.setData(byOwnerName);
       message.setStatus(HttpStatus.OK);
       message.setResponseMessage("Property Fetched By Owner");
        return message;
    }

    /**
     * Validates if the file type is a valid image format
     */
    private boolean isValidImageType(String contentType) {
        if (contentType == null) {
            return false;
        }
        return contentType.startsWith("image/");
    }
}



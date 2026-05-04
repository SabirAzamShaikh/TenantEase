package com.example.TenantEase.controller;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.PropertyRequestDto;
import com.example.TenantEase.dto.PropertyResponseDto;
import com.example.TenantEase.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property")
@RequiredArgsConstructor
@Slf4j
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping(value = "/addProperty", consumes = {"multipart/form-data"})
    public Message<PropertyResponseDto> addProperty(@ModelAttribute PropertyRequestDto property) {
        log.info("In PropertyController with request Dto {}", property);
        try {
            return propertyService.addProperty(property);
        } catch (Exception e) {
            log.error("Error in addProperty: ", e);
            Message<PropertyResponseDto> errorMessage = new Message<>();
            errorMessage.setResponseMessage("Failed to add property: " + e.getMessage());
            errorMessage.setStatus(HttpStatus.BAD_REQUEST);
            return errorMessage;
        }
    }

    @GetMapping("/owner")
    public Message<List<PropertyResponseDto>> getPropertyByOwner(@RequestParam String ownerName) {
        return propertyService.getPropertyByOwner(ownerName);
    }

    @GetMapping("/getProperty")
    public Message<PropertyResponseDto> getProperty(@RequestParam Long propertyId) {
        try {
            return propertyService.getPropertyById(propertyId);
        } catch (Exception e) {
            log.error("Error in getProperty: ", e);
            Message<PropertyResponseDto> errorMessage = new Message<>();
            errorMessage.setResponseMessage("Failed to fetch property: " + e.getMessage());
            errorMessage.setStatus(HttpStatus.NOT_FOUND);
            return errorMessage;
        }
    }

    @GetMapping("/getAllProperty")
    public Message<List<PropertyResponseDto>> getAllProperty(@RequestParam int page, @RequestParam int size) {
        try {
            return propertyService.getAllProperty(page, size);
        } catch (Exception e) {
            log.error("Error in getAllProperty: ", e);
            Message<List<PropertyResponseDto>> errorMessage = new Message<>();
            errorMessage.setResponseMessage("Failed to fetch properties: " + e.getMessage());
            errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return errorMessage;
        }
    }

    @DeleteMapping("/deleteProperty")
    public Message<String> deleteProperty(@RequestParam Long propertyId) {
        try {
            return propertyService.deleteProperty(propertyId);
        } catch (Exception e) {
            log.error("Error in deleteProperty: ", e);
            Message<String> errorMessage = new Message<>();
            errorMessage.setResponseMessage("Failed to delete property: " + e.getMessage());
            errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return errorMessage;
        }
    }

}

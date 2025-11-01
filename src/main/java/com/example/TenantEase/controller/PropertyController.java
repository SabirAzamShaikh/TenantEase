package com.example.TenantEase.controller;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.PropertyRequestDto;
import com.example.TenantEase.dto.PropertyResponseDto;
import com.example.TenantEase.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/property")
@RequiredArgsConstructor
@Slf4j
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping(value = "/addProperty", consumes = { "multipart/form-data" })
    public Message<PropertyResponseDto> addProperty(@ModelAttribute PropertyRequestDto property) {
      log.info("In PropertyController with request Dto {}",property);
        return propertyService.addProperty(property);
    }

    @GetMapping("/getProperty")
    public Message<PropertyResponseDto> getProperty(@RequestParam Long propertyId) {
        return propertyService.getPropertyById((propertyId));
    }

    @GetMapping("/getAllProperty")
    public Message<List<PropertyResponseDto>> getAllProperty(@RequestParam int page, @RequestParam int size) {
        return propertyService.getAllProperty(page, size);
    }

    @DeleteMapping("/deleteProperty")
    public Message<String> deleteProperty(@RequestParam Long propertyId) {
        return propertyService.deleteProperty(propertyId);
    }

}

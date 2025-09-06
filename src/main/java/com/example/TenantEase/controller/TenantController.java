package com.example.TenantEase.controller;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;
import com.example.TenantEase.model.Tenant;
import com.example.TenantEase.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenant")
public class TenantController {
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @RequestMapping("/addTenant")
    public ResponseEntity<Message<TenantResponseDto>> addTenant(@RequestBody TenantRequestDto tenant) {
        Message<TenantResponseDto> message = new Message<>();
        try {
            message = tenantService.addTenant(tenant);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("Internal Server Error Occurs at addTenant()");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @GetMapping("/getAllTenant")
    public ResponseEntity<Message<List<TenantResponseDto>>> getAllTenant() {
        Message<List<TenantResponseDto>> message = new Message<>();
        try {
            message = tenantService.getAllTenant();
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setResponseMessage("Internal Server Error Occurs at getAllTenant() in TenantController " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<Message<TenantResponseDto>> getTenantById(@RequestParam("id") long id) {
        Message<TenantResponseDto> message = new Message<>();
        try {
            message = tenantService.getTenantById(id);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setResponseMessage("Internal Server Error Occurs at getTenantById() in TenantController " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}


package com.example.TenantEase.controller;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;
import com.example.TenantEase.dto.TenantUpdateRequestDto;
import com.example.TenantEase.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenant")
@Slf4j
public class TenantController {
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/addTenant")
    public ResponseEntity<Message<TenantResponseDto>> addTenant(@RequestBody TenantRequestDto tenant,
            @RequestParam Long roomId) {
        Message<TenantResponseDto> message = new Message<>();
        try {
            message = tenantService.addTenant(tenant, roomId);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("Internal Server Error Occurs at addTenant()");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PutMapping("/updateTenant")
    public Message<TenantResponseDto> updateTenant(@RequestBody TenantUpdateRequestDto tenantUpdateDto) {
        log.info("In TenantController updateTenant with request Dto {}", tenantUpdateDto);
        try {
            return tenantService.updateTenant(tenantUpdateDto);
        } catch (Exception e) {
            log.error("Error in updateTenant: ", e);
            Message<TenantResponseDto> errorMessage = new Message<>();
            errorMessage.setResponseMessage("Failed to update tenant: " + e.getMessage());
            errorMessage.setStatus(HttpStatus.BAD_REQUEST);
            return errorMessage;
        }
    }



    // SUPER_ADMIN API to see All tenant
    @GetMapping("/getAllTenant")
    public ResponseEntity<Message<List<TenantResponseDto>>> getAllTenant() {
        Message<List<TenantResponseDto>> message = new Message<>();
        try {
            message = tenantService.getAllTenant();
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setResponseMessage(
                    "Internal Server Error Occurs at getAllTenant() in TenantController " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @GetMapping("/getAllTenantAsPerOwner")
    public ResponseEntity<Message<List<TenantResponseDto>>> getAllTenantByOwner(
            @RequestParam("ownerName") String ownerName) {
        Message<List<TenantResponseDto>> message = new Message<>();
        try {
            message = tenantService.getAllTenantByOwner(ownerName);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setResponseMessage(
                    "Internal Server Error Occurs at getAllTenant() in TenantController " + e.getMessage());
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
            message.setResponseMessage(
                    "Internal Server Error Occurs at getTenantById() in TenantController " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<Message<TenantResponseDto>> getTenantByEmail(@RequestParam("email") String email) {
        Message<TenantResponseDto> message = new Message<>();
        try {
            message = tenantService.getTenantByEmail(email);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setResponseMessage(
                    "Internal Server Error Occurs at getTenantByEmail() in TenantController " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}

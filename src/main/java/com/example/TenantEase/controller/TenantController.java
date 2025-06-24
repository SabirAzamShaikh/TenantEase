package com.example.TenantEase.controller;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.model.Tenant;
import com.example.TenantEase.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenant")
public class TenantController {
    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @RequestMapping("/addTenant")
    public ResponseEntity<Message<Tenant>> addTenant(@RequestBody Tenant tenant) {
        Message<Tenant> message = new Message<>();
        try {
            message = tenantService.addTenant(tenant);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("Internal Server Error Occurs at addTenant()");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
}

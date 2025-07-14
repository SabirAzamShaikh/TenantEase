package com.example.TenantEase.controller;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.PermissionRequestDto;
import com.example.TenantEase.model.Permission;
import com.example.TenantEase.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    private final PermissionService service;

    public PermissionController(PermissionService service) {
        this.service = service;
    }

    @PostMapping("/createPermission")
    public ResponseEntity<Message<Permission>> savePermission(@RequestBody PermissionRequestDto requestDto) {
        Message<Permission> message = new Message<>();
        try {
            message = service.savePermission(requestDto);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Message<List<Permission>>> savePermission() {
        Message<List<Permission>> message = new Message<>();
        try {
            message = service.getAll();
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

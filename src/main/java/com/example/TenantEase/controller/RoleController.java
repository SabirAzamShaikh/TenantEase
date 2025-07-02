package com.example.TenantEase.controller;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.RoleRequestDto;
import com.example.TenantEase.model.Role;
import com.example.TenantEase.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/createRole")
    public ResponseEntity<Message<Role>> createRole(@RequestBody RoleRequestDto dto) {
        Message<Role> message = new Message<>();
        try {
            message = roleService.createRole(dto);
            return ResponseEntity.status(message.getStatus()).body(message);
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs at createRole() in RoleController");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

}

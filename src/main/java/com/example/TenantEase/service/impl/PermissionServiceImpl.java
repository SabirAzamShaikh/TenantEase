package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.PermissionRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.PermissionRequestDto;
import com.example.TenantEase.model.Permission;
import com.example.TenantEase.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository repository;

    public PermissionServiceImpl(PermissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message<Permission> savePermission(PermissionRequestDto requestDto) {
        Message<Permission> message = new Message<>();
        try {
            Permission permission = new Permission().setName(requestDto.getName()).setDescription(requestDto.getDescription())
                    .setCreatedAt(LocalDateTime.now()).setCreatedBy("SUPER_ADMIN");
            Permission savedPermission = repository.save(permission);
            message.setResponseMessage("Data Saved SuccessFully");
            message.setData(savedPermission);
            message.setStatus(HttpStatus.CREATED);
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs At savePermission in permissionServiceImpl");
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }
@Override
public Message<Permission> getById(int id) {
        Message<Permission> message = new Message<>();
        try {
            Permission permission = repository.findById(id).orElse(null);
            if (permission != null) {
                message.setResponseMessage("Data Retrieved SuccessFully");
                message.setData(permission);
                message.setStatus(HttpStatus.OK);
                return message;
            } else {
                message.setResponseMessage("Permission Not Found With Id " + id);
                message.setStatus(HttpStatus.NOT_FOUND);
                return message;
            }
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs At getById() in permissionServiceImpl");
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }

    @Override
    public Message<List<Permission>> getAll() {
        Message<List<Permission>> message = new Message<>();
        try {
            List<Permission> permission = repository.findAll();
            message.setResponseMessage("Data Retrieved SuccessFully");
            message.setData(permission);
            message.setStatus(HttpStatus.OK);
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs At getAll() in permissionServiceImpl");
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }
}

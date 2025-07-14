package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.PermissionRequestDto;
import com.example.TenantEase.model.Permission;

import java.util.List;

public interface PermissionService {
    Message<Permission> savePermission(PermissionRequestDto requestDto);

    Message<Permission> getById(int id);

    Message<List<Permission>> getAll();
}

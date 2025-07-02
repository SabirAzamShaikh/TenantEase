package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.RoleRequestDto;
import com.example.TenantEase.model.Role;

public interface RoleService {
    Message<Role> createRole(RoleRequestDto dto);
}

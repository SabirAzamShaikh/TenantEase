package com.example.TenantEase.mapper;

import com.example.TenantEase.dto.RoleRequestDto;
import com.example.TenantEase.model.Role;

public interface RoleMapper {
    Role requestDtoToEntity(RoleRequestDto dto);
}

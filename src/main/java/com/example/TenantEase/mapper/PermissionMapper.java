package com.example.TenantEase.mapper;

import com.example.TenantEase.dto.PermissionRequestDto;
import com.example.TenantEase.model.Permission;

public interface PermissionMapper {

Permission dtoToEntity(PermissionRequestDto requestDto);
}

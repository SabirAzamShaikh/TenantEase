package com.example.TenantEase.mapper.impl;

import com.example.TenantEase.dto.PermissionRequestDto;
import com.example.TenantEase.mapper.PermissionMapper;
import com.example.TenantEase.model.Permission;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PermissionMapperImpl implements PermissionMapper {
    @Override
    public Permission dtoToEntity(PermissionRequestDto requestDto) {
        return new Permission().setName(requestDto.getName()).setDescription(requestDto.getDescription())
                .setCreatedAt(LocalDateTime.now()).setCreatedBy("SUPER_ADMIN");
    }
}

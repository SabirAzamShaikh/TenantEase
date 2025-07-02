package com.example.TenantEase.mapper.impl;

import com.example.TenantEase.Repository.PermissionRepository;
import com.example.TenantEase.dto.RoleRequestDto;
import com.example.TenantEase.mapper.RoleMapper;
import com.example.TenantEase.model.Permission;
import com.example.TenantEase.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role requestDtoToEntity(RoleRequestDto dto) {
        Set<Permission> permissions = new HashSet<>();
        Role role = new Role().setCreatedAt(LocalDateTime.now()).setDescription(dto.getDescription()).setCreatedBy("SUPER_ADMIN");
        permissions = dto.getPermissions().stream().map(x -> new Permission().setCreatedAt(LocalDateTime.now())
                .setDescription(x.getDescription()).setName(x.getName())
                .setCreatedBy("SUPER_ADMIN")).collect(Collectors.toSet());
        role.setPermissions(permissions);
        return role;
    }

}

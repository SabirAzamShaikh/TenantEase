package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.PermissionRepository;
import com.example.TenantEase.Repository.RoleRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.RoleRequestDto;
import com.example.TenantEase.mapper.RoleMapper;
import com.example.TenantEase.model.Permission;
import com.example.TenantEase.model.Role;
import com.example.TenantEase.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleMapper roleMapper, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional
    public Message<Role> createRole(RoleRequestDto dto) {
        Message<Role> message = new Message<>();
        try {
            Role  role = roleMapper.requestDtoToEntity(dto);
            Set<Permission> savedPermissions = new HashSet<>(permissionRepository.saveAll(role.getPermissions()));
            Role savedRole = roleRepository.save(role.setPermissions(savedPermissions));
            message.setResponseMessage("Role Created SuccessFully !");
            message.setStatus(HttpStatus.CREATED);
            message.setData(savedRole);
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs At createRole() in RoleServiceImpl ");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 🔁 important
            log.error("Error Occurs At createRole() in RoleServiceImpl {}",e.getMessage());
             message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }
}

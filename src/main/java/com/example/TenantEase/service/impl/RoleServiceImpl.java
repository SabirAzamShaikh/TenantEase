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
import java.util.List;
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
            Role role = roleMapper.requestDtoToEntity(dto);
            Set<Permission> savedPermissions = new HashSet<>(permissionRepository.saveAll(role.getPermissions()));
            Role savedRole = roleRepository.save(role.setPermissions(savedPermissions));
            message.setResponseMessage("Role Created SuccessFully !");
            message.setStatus(HttpStatus.CREATED);
            message.setData(savedRole);
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs At createRole() in RoleServiceImpl ");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 🔁 important
            log.error("Error Occurs At createRole() in RoleServiceImpl {}", e.getMessage());
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }

    @Override
    public Message<Role> getById(int id) {
        Message<Role> message = new Message<>();
        try {
            Role role = roleRepository.findById(id).orElse(null);
            if (role != null) {
                message.setStatus(HttpStatus.OK);
                message.setResponseMessage("Fetched SuccessFully ");
                message.setData(role);
                return message;
            } else {
                message.setStatus(HttpStatus.NOT_FOUND);
                message.setResponseMessage("Role Not Found With Role ID :" + id);
                return message;
            }
        } catch (Exception e) {
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("Error Occurs At getById() in RoleServiceImpl");
            log.error("Error Occurs At getById() in RoleServiceImpl {}", e.getMessage());
            return message;
        }
    }

    @Override
    public Message<List<Role>> getAll() {
        Message<List<Role>> message = new Message<>();
        try {
            List<Role> role = roleRepository.findAll();
            message.setStatus(HttpStatus.OK);
            message.setResponseMessage("Fetched SuccessFully ");
            message.setData(role);
            return message;
        } catch (Exception e) {
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("Error Occurs At getById() in RoleServiceImpl");
            log.error("Error Occurs At getAll() in RoleServiceImpl {}", e.getMessage());
            return message;
        }
    }
}

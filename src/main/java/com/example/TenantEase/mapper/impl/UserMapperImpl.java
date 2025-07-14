package com.example.TenantEase.mapper.impl;

import com.example.TenantEase.dto.UserRequestDto;
import com.example.TenantEase.mapper.UserMapper;
import com.example.TenantEase.model.Role;
import com.example.TenantEase.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User RequestDtoToEntity(UserRequestDto dto, List<Role> roles) {
        HashSet<Role> hs = new HashSet<>(roles);
        Random random = new Random();
        return new User().setUserType(dto.getUserType()).setCreatedBy("SUPER_ADMIN")
                .setEmail(dto.getEmail()).setContactNumber(dto.getContactNumber())
                .setFullName(dto.getFullName()).setStatus("Active").setRoles(hs)
                .setCreatedAt(LocalDateTime.now()).setPassword(dto.getPassword());
                //.setPassword(dto.getFullName().replaceAll("\\s+", "") + random.nextInt(4));
    }
}

package com.example.TenantEase.mapper;

import com.example.TenantEase.dto.UserRequestDto;
import com.example.TenantEase.model.Role;
import com.example.TenantEase.model.User;

import java.util.List;

public interface UserMapper {
public User RequestDtoToEntity(UserRequestDto dto, List<Role> roles
);
}

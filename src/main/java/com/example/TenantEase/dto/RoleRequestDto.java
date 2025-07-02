package com.example.TenantEase.dto;

import com.example.TenantEase.model.Permission;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;
@Data
@Accessors(chain = true)
public class RoleRequestDto {

    private String description;
    private Set<PermissionRequestDto> permissions;

}

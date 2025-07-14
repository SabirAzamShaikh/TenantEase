package com.example.TenantEase.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PermissionRequestDto {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = true, length = 255)
    private String description;

}

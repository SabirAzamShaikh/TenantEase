package com.example.TenantEase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
public class UserRequestDto {
    private String fullName;
    private String password;
    private String userType;
    private String email;
    private String contactNumber;
    @NotNull(message = "Role ID cannot be null")
    private List<Integer> roleIds;
}

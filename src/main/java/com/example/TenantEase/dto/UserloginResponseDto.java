package com.example.TenantEase.dto;

import com.example.TenantEase.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserloginResponseDto {
private String token;
private User user;
}

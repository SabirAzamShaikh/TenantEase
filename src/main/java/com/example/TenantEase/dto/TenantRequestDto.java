package com.example.TenantEase.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TenantRequestDto {
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    private String phoneNumber;

    @NotBlank(message = "Aadhar card number is required")
    @Pattern(regexp = "^[0-9]{12}$", message = "Aadhar card number must be 12 digits")
    private String adharNumber;

    @NotBlank(message = "Room number is required")
    @Pattern(regexp = "^[0-9]+$", message = "Room number must contain only digits")
    private String roomNumber;

    private String floorNumber;

    private String depositeAmount;
}

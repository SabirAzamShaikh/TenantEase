package com.example.TenantEase.dto;

import jakarta.validation.constraints.*;
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

    private Long depositeAmount;
    @NotNull(message = "Tenant Rent is required")
    @Positive(message = "Tenant Rent must be > 0")

    private Long tenantRent;

    @NotNull(message = "Payment Day is required")
    @Min(value = 1, message = "Payment day must be at least 1")
    @Max(value = 28, message = "Payment day must not exceed 28 to avoid invalid dates")
    private Integer rentPaymentDay;

}

package com.example.TenantEase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantUpdateRequestDto {
    private Long tenantId;
    private String tenantName;
    private String email;
    private String phoneNumber;
    private String aadharNumber;
    private Long rentAmount;
    private String leaseStartDate;
    private String leaseEndDate;
    private String emergencyContact;
    private String occupationStatus;
}

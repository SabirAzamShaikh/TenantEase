package com.example.TenantEase.dto;

import com.example.TenantEase.enums.BillingCycle;
import lombok.Data;

@Data
public class AdminPlanRequestDto {
    private String planName;
    private String description;
    private Long price;
    private BillingCycle billingCycle;
    private Long maxProperties;
    private Long maxRooms;
    private Long maxTenants;
}

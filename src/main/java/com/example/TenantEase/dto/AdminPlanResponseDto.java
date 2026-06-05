package com.example.TenantEase.dto;

import com.example.TenantEase.enums.BillingCycle;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminPlanResponseDto {
    private Long id;
    private String planName;
    private String description;
    private Long price;
    private BillingCycle billingCycle;
    private Long maxProperties;
    private Long maxRooms;
    private Long maxTenants;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

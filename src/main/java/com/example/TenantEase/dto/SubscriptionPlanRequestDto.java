package com.example.TenantEase.dto;

import lombok.Data;

@Data
public class SubscriptionPlanRequestDto {
    private String planName;
    private Long maxProperties;
    private Long maxRooms;
    private Long maxTenants;
    private Long price;
}
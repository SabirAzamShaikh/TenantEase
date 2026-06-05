package com.example.TenantEase.dto;

import com.example.TenantEase.enums.SubscriptionStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserSubscriptionResponseDto {
    private Long id;
    private AdminPlanResponseDto plan;
    private SubscriptionStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean autoRenew;
    private LocalDateTime activatedAt;
    private LocalDateTime cancelledAt;
}

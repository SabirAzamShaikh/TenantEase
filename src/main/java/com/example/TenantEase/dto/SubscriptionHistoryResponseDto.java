package com.example.TenantEase.dto;

import com.example.TenantEase.enums.SubscriptionAction;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubscriptionHistoryResponseDto {
    private Long id;
    private String oldPlanName;
    private String newPlanName;
    private SubscriptionAction actionType;
    private Long amountPaid;
    private LocalDateTime actionDate;
    private String remarks;
}

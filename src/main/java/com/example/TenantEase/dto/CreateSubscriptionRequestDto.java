package com.example.TenantEase.dto;

import com.example.TenantEase.enums.PlanType;

public class CreateSubscriptionRequestDto {
    private Long userId;
    private PlanType planType;

    public CreateSubscriptionRequestDto() {}

    public CreateSubscriptionRequestDto(Long userId, PlanType planType) {
        this.userId = userId;
        this.planType = planType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }
}

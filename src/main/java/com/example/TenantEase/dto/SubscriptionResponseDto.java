package com.example.TenantEase.dto;

import com.example.TenantEase.enums.PlanType;
import com.example.TenantEase.enums.SubscriptionStatus;
import java.time.LocalDateTime;

public class SubscriptionResponseDto {
    private Long subscriptionId;
    private PlanType planType;
    private SubscriptionStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int propertiesUsed;
    private int roomsUsed;
    private int tenantsUsed;

    public SubscriptionResponseDto() {}

    public SubscriptionResponseDto(PlanType planType, SubscriptionStatus status) {
        this.planType = planType;
        this.status = status;
    }

    // Getters and Setters
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getPropertiesUsed() {
        return propertiesUsed;
    }

    public void setPropertiesUsed(int propertiesUsed) {
        this.propertiesUsed = propertiesUsed;
    }

    public int getRoomsUsed() {
        return roomsUsed;
    }

    public void setRoomsUsed(int roomsUsed) {
        this.roomsUsed = roomsUsed;
    }

    public int getTenantsUsed() {
        return tenantsUsed;
    }

    public void setTenantsUsed(int tenantsUsed) {
        this.tenantsUsed = tenantsUsed;
    }
}

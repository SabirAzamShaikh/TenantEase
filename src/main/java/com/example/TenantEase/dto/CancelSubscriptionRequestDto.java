package com.example.TenantEase.dto;

public class CancelSubscriptionRequestDto {
    private Long subscriptionId;
    private String cancellationReason;

    public CancelSubscriptionRequestDto() {}

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
}

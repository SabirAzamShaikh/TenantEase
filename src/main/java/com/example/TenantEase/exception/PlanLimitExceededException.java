package com.example.TenantEase.exception;

public class PlanLimitExceededException extends RuntimeException {
    private String resourceType;
    private int currentUsage;
    private int planLimit;

    public PlanLimitExceededException(String resourceType, int currentUsage, int planLimit) {
        super(String.format("Plan limit exceeded for %s. Used: %d/%d", resourceType, currentUsage, planLimit));
        this.resourceType = resourceType;
        this.currentUsage = currentUsage;
        this.planLimit = planLimit;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getCurrentUsage() {
        return currentUsage;
    }

    public int getPlanLimit() {
        return planLimit;
    }
}

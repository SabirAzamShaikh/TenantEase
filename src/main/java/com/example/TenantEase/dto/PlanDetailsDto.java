package com.example.TenantEase.dto;

public class PlanDetailsDto {
    private String planType;
    private String displayName;
    private int monthlyPrice;
    private int maxProperties;
    private int maxRooms;
    private int maxTenants;
    private boolean isCurrent;

    public PlanDetailsDto(String planType, String displayName, int monthlyPrice, 
                         int maxProperties, int maxRooms, int maxTenants, boolean isCurrent) {
        this.planType = planType;
        this.displayName = displayName;
        this.monthlyPrice = monthlyPrice;
        this.maxProperties = maxProperties;
        this.maxRooms = maxRooms;
        this.maxTenants = maxTenants;
        this.isCurrent = isCurrent;
    }

    // Getters
    public String getPlanType() { return planType; }
    public String getDisplayName() { return displayName; }
    public int getMonthlyPrice() { return monthlyPrice; }
    public int getMaxProperties() { return maxProperties; }
    public int getMaxRooms() { return maxRooms; }
    public int getMaxTenants() { return maxTenants; }
    public boolean isCurrent() { return isCurrent; }
}

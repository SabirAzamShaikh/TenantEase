package com.example.TenantEase.dto;

public class UsageSummaryDto {
    private int propertiesUsed;
    private int maxProperties;
    private int roomsUsed;
    private int maxRooms;
    private int tenantsUsed;
    private int maxTenants;

    public UsageSummaryDto(int propertiesUsed, int maxProperties, int roomsUsed, int maxRooms, int tenantsUsed, int maxTenants) {
        this.propertiesUsed = propertiesUsed;
        this.maxProperties = maxProperties;
        this.roomsUsed = roomsUsed;
        this.maxRooms = maxRooms;
        this.tenantsUsed = tenantsUsed;
        this.maxTenants = maxTenants;
    }

    public UsageSummaryDto() {}

    // Getters and Setters
    public int getPropertiesUsed() {
        return propertiesUsed;
    }

    public void setPropertiesUsed(int propertiesUsed) {
        this.propertiesUsed = propertiesUsed;
    }

    public int getMaxProperties() {
        return maxProperties;
    }

    public void setMaxProperties(int maxProperties) {
        this.maxProperties = maxProperties;
    }

    public int getRoomsUsed() {
        return roomsUsed;
    }

    public void setRoomsUsed(int roomsUsed) {
        this.roomsUsed = roomsUsed;
    }

    public int getMaxRooms() {
        return maxRooms;
    }

    public void setMaxRooms(int maxRooms) {
        this.maxRooms = maxRooms;
    }

    public int getTenantsUsed() {
        return tenantsUsed;
    }

    public void setTenantsUsed(int tenantsUsed) {
        this.tenantsUsed = tenantsUsed;
    }

    public int getMaxTenants() {
        return maxTenants;
    }

    public void setMaxTenants(int maxTenants) {
        this.maxTenants = maxTenants;
    }

    // Convenience methods
    public double getPropertyUsagePercentage() {
        return maxProperties > 0 ? (propertiesUsed * 100.0) / maxProperties : 0;
    }

    public double getRoomUsagePercentage() {
        return maxRooms > 0 ? (roomsUsed * 100.0) / maxRooms : 0;
    }

    public double getTenantUsagePercentage() {
        return maxTenants > 0 ? (tenantsUsed * 100.0) / maxTenants : 0;
    }
}

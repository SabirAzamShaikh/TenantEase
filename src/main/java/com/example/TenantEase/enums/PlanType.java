package com.example.TenantEase.enums;

public enum PlanType {
    FREE(0, 1, 5, 5),           // ₹0, 1 property, 5 rooms, 5 tenants
    PRO(999, 10, 500, 1000),    // ₹999/month, 10 properties, 500 rooms, 1000 tenants
    PREMIUM(2999, -1, -1, -1);  // ₹2999/month, unlimited properties, rooms & tenants (-1 = unlimited)

    private final int priceInPaisa;  // Razorpay uses paisa (1 rupee = 100 paisa)
    private final int maxProperties;
    private final int maxRooms;
    private final int maxTenants;

    PlanType(int priceInRupees, int maxProperties, int maxRooms, int maxTenants) {
        this.priceInPaisa = priceInRupees * 100;  // Convert to paisa for Razorpay
        this.maxProperties = maxProperties;
        this.maxRooms = maxRooms;
        this.maxTenants = maxTenants;
    }

    public int getPriceInPaisa() {
        return priceInPaisa;
    }

    public int getPriceInRupees() {
        return priceInPaisa / 100;
    }

    public int getMaxProperties() {
        return maxProperties;
    }

    public int getMaxRooms() {
        return maxRooms;
    }

    public int getMaxTenants() {
        return maxTenants;
    }

    public boolean isUnlimited(String resourceType) {
        return switch (resourceType) {
            case "PROPERTY" -> maxProperties == -1;
            case "ROOM" -> maxRooms == -1;
            case "TENANT" -> maxTenants == -1;
            default -> false;
        };
    }
}

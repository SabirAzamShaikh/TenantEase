package com.example.TenantEase.service;

import com.example.TenantEase.enums.ResourceType;

public interface PlanUsageService {
    void incrementUsage(int userId, ResourceType resourceType);
    void decrementUsage(int userId, ResourceType resourceType);
}

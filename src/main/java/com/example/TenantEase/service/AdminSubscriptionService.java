package com.example.TenantEase.service;

import com.example.TenantEase.dto.AdminPlanRequestDto;
import com.example.TenantEase.dto.AdminPlanResponseDto;
import com.example.TenantEase.dto.Message;

import java.util.List;

public interface AdminSubscriptionService {
    Message<AdminPlanResponseDto> createPlan(AdminPlanRequestDto request);
    Message<AdminPlanResponseDto> updatePlan(Long planId, AdminPlanRequestDto request);
    Message<String> activatePlan(Long planId);
    Message<String> deactivatePlan(Long planId);
    Message<List<AdminPlanResponseDto>> getAllPlans();
    Message<Object> getAllSubscriptions();
    Message<Object> getAllPayments();
    Message<Object> getRevenueAnalytics();
}

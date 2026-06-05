package com.example.TenantEase.service;

import com.example.TenantEase.dto.AdminPlanResponseDto;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.SubscriptionHistoryResponseDto;
import com.example.TenantEase.dto.UserSubscriptionResponseDto;

import java.util.List;

public interface SubscriptionService {
    Message<UserSubscriptionResponseDto> getCurrentSubscription(String email);
    Message<List<SubscriptionHistoryResponseDto>> getSubscriptionHistory(String email);
    Message<List<AdminPlanResponseDto>> getAvailablePlans();
    Message<String> initiateUpgrade(String email, Long planId);
    Message<String> cancelSubscription(String email);
    Message<Object> getPaymentHistory(String email);
}

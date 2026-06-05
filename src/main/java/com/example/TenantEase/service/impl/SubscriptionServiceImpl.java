package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.*;
import com.example.TenantEase.dto.AdminPlanResponseDto;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.SubscriptionHistoryResponseDto;
import com.example.TenantEase.dto.UserSubscriptionResponseDto;
import com.example.TenantEase.enums.SubscriptionStatus;
import com.example.TenantEase.exception.ResourceNotFoundException;
import com.example.TenantEase.model.SubscriptionPlan;
import com.example.TenantEase.model.User;
import com.example.TenantEase.model.UserSubscription;
import com.example.TenantEase.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionPlanRepository planRepository;
    private final SubscriptionHistoryRepository historyRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Message<UserSubscriptionResponseDto> getCurrentSubscription(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserSubscription subscription = userSubscriptionRepository.findByUser_UserIdAndStatus(user.getUserId(), SubscriptionStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Active subscription not found"));

        UserSubscriptionResponseDto dto = new UserSubscriptionResponseDto();
        dto.setId(subscription.getId());
        dto.setStatus(subscription.getStatus());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setAutoRenew(subscription.isAutoRenew());
        dto.setActivatedAt(subscription.getActivatedAt());
        dto.setCancelledAt(subscription.getCancelledAt());
        
        AdminPlanResponseDto planDto = new AdminPlanResponseDto();
        planDto.setId(subscription.getSubscriptionPlan().getId());
        planDto.setPlanName(subscription.getSubscriptionPlan().getPlanName());
        planDto.setMaxProperties(subscription.getSubscriptionPlan().getMaxProperties());
        planDto.setMaxRooms(subscription.getSubscriptionPlan().getMaxRooms());
        planDto.setMaxTenants(subscription.getSubscriptionPlan().getMaxTenants());
        dto.setPlan(planDto);

        Message<UserSubscriptionResponseDto> message = new Message<>();
        message.setData(dto);
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public Message<List<SubscriptionHistoryResponseDto>> getSubscriptionHistory(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        List<SubscriptionHistoryResponseDto> history = historyRepository.findByUser_UserId(user.getUserId())
                .stream().map(h -> {
                    SubscriptionHistoryResponseDto dto = new SubscriptionHistoryResponseDto();
                    dto.setId(h.getId());
                    if(h.getOldPlan() != null) dto.setOldPlanName(h.getOldPlan().getPlanName());
                    if(h.getNewPlan() != null) dto.setNewPlanName(h.getNewPlan().getPlanName());
                    dto.setActionType(h.getActionType());
                    dto.setAmountPaid(h.getAmountPaid());
                    dto.setActionDate(h.getActionDate());
                    dto.setRemarks(h.getRemarks());
                    return dto;
                }).collect(Collectors.toList());

        Message<List<SubscriptionHistoryResponseDto>> message = new Message<>();
        message.setData(history);
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public Message<List<AdminPlanResponseDto>> getAvailablePlans() {
        List<AdminPlanResponseDto> plans = planRepository.findAll().stream()
                .filter(SubscriptionPlan::isActive)
                .map(plan -> {
                    AdminPlanResponseDto dto = new AdminPlanResponseDto();
                    dto.setId(plan.getId());
                    dto.setPlanName(plan.getPlanName());
                    dto.setDescription(plan.getDescription());
                    dto.setPrice(plan.getPrice());
                    dto.setBillingCycle(plan.getBillingCycle());
                    dto.setMaxProperties(plan.getMaxProperties());
                    dto.setMaxRooms(plan.getMaxRooms());
                    dto.setMaxTenants(plan.getMaxTenants());
                    return dto;
                }).collect(Collectors.toList());
        Message<List<AdminPlanResponseDto>> message = new Message<>();
        message.setData(plans);
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional
    public Message<String> initiateUpgrade(String email, Long planId) {
        User user = userRepository.findByEmail(email).orElseThrow();
        SubscriptionPlan plan = planRepository.findById(planId).orElseThrow();

        String dummySubId = "DUMMY_SUB_" + UUID.randomUUID().toString();
        String paymentLink = "https://dummy-payment.razorpay.com/" + UUID.randomUUID().toString();

        UserSubscription pendingSub = new UserSubscription();
        pendingSub.setUser(user);
        pendingSub.setSubscriptionPlan(plan);
        pendingSub.setStatus(SubscriptionStatus.PENDING);
        pendingSub.setRazorpaySubscriptionId(dummySubId);
        pendingSub.setCreatedAt(LocalDateTime.now());
        userSubscriptionRepository.save(pendingSub);

        Message<String> message = new Message<>();
        message.setData(paymentLink);
        message.setResponseMessage("Dummy Razorpay payment link generated. Webhook testing ready with ID: " + dummySubId);
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional
    public Message<String> cancelSubscription(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        UserSubscription subscription = userSubscriptionRepository.findByUser_UserIdAndStatus(user.getUserId(), SubscriptionStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Active subscription not found"));

        subscription.setAutoRenew(false);
        userSubscriptionRepository.save(subscription);
        // Note: For actual cancellation, you'd wait for billing cycle to end or do it immediately.
        // As per prompt, webhook handles actual cancellation logic, but we can set autoRenew to false here.
        
        Message<String> message = new Message<>();
        message.setData("Subscription cancelled (auto-renew disabled).");
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public Message<Object> getPaymentHistory(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Message<Object> message = new Message<>();
        message.setData(paymentTransactionRepository.findByUser_UserId(user.getUserId()));
        message.setStatus(HttpStatus.OK);
        return message;
    }
}

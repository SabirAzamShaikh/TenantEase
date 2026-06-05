package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.PaymentTransactionRepository;
import com.example.TenantEase.Repository.SubscriptionPlanRepository;
import com.example.TenantEase.Repository.UserSubscriptionRepository;
import com.example.TenantEase.dto.AdminPlanRequestDto;
import com.example.TenantEase.dto.AdminPlanResponseDto;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.exception.ResourceNotFoundException;
import com.example.TenantEase.model.SubscriptionPlan;
import com.example.TenantEase.service.AdminSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminSubscriptionServiceImpl implements AdminSubscriptionService {

    private final SubscriptionPlanRepository planRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Override
    @Transactional
    public Message<AdminPlanResponseDto> createPlan(AdminPlanRequestDto request) {
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setPlanName(request.getPlanName());
        plan.setDescription(request.getDescription());
        plan.setPrice(request.getPrice());
        plan.setBillingCycle(request.getBillingCycle());
        plan.setMaxProperties(request.getMaxProperties());
        plan.setMaxRooms(request.getMaxRooms());
        plan.setMaxTenants(request.getMaxTenants());
        plan.setCreatedAt(LocalDateTime.now());
        plan = planRepository.save(plan);

        Message<AdminPlanResponseDto> message = new Message<>();
        message.setData(mapToDto(plan));
        message.setResponseMessage("Plan created successfully");
        message.setStatus(HttpStatus.CREATED);
        return message;
    }

    @Override
    @Transactional
    public Message<AdminPlanResponseDto> updatePlan(Long planId, AdminPlanRequestDto request) {
        SubscriptionPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

        plan.setPlanName(request.getPlanName());
        plan.setDescription(request.getDescription());
        plan.setPrice(request.getPrice());
        plan.setBillingCycle(request.getBillingCycle());
        plan.setMaxProperties(request.getMaxProperties());
        plan.setMaxRooms(request.getMaxRooms());
        plan.setMaxTenants(request.getMaxTenants());

        plan = planRepository.save(plan);

        Message<AdminPlanResponseDto> message = new Message<>();
        message.setData(mapToDto(plan));
        message.setResponseMessage("Plan updated successfully");
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional
    public Message<String> activatePlan(Long planId) {
        SubscriptionPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
        plan.setActive(true);
        planRepository.save(plan);
        Message<String> message = new Message<>();
        message.setData("Plan activated");
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional
    public Message<String> deactivatePlan(Long planId) {
        SubscriptionPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
        plan.setActive(false);
        planRepository.save(plan);
        Message<String> message = new Message<>();
        message.setData("Plan deactivated");
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public Message<List<AdminPlanResponseDto>> getAllPlans() {
        List<AdminPlanResponseDto> plans = planRepository.findAll()
                .stream().map(this::mapToDto).collect(Collectors.toList());
        Message<List<AdminPlanResponseDto>> message = new Message<>();
        message.setData(plans);
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public Message<Object> getAllSubscriptions() {
        Message<Object> message = new Message<>();
        message.setData(userSubscriptionRepository.findAll());
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public Message<Object> getAllPayments() {
        Message<Object> message = new Message<>();
        message.setData(paymentTransactionRepository.findAll());
        message.setStatus(HttpStatus.OK);
        return message;
    }

    @Override
    @Transactional(readOnly = true)
    public Message<Object> getRevenueAnalytics() {
        // Simplified revenue analytics
        Long totalRevenue = paymentTransactionRepository.findAll().stream()
                .filter(p -> p.getPaymentStatus() == com.example.TenantEase.enums.PaymentStatus.SUCCESS)
                .mapToLong(com.example.TenantEase.model.PaymentTransaction::getAmount)
                .sum();
        
        Message<Object> message = new Message<>();
        message.setData("Total Revenue: " + totalRevenue);
        message.setStatus(HttpStatus.OK);
        return message;
    }

    private AdminPlanResponseDto mapToDto(SubscriptionPlan plan) {
        AdminPlanResponseDto dto = new AdminPlanResponseDto();
        dto.setId(plan.getId());
        dto.setPlanName(plan.getPlanName());
        dto.setDescription(plan.getDescription());
        dto.setPrice(plan.getPrice());
        dto.setBillingCycle(plan.getBillingCycle());
        dto.setMaxProperties(plan.getMaxProperties());
        dto.setMaxRooms(plan.getMaxRooms());
        dto.setMaxTenants(plan.getMaxTenants());
        dto.setActive(plan.isActive());
        dto.setCreatedAt(plan.getCreatedAt());
        dto.setUpdatedAt(plan.getUpdatedAt());
        return dto;
    }
}

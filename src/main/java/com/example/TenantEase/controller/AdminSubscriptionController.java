package com.example.TenantEase.controller;

import com.example.TenantEase.dto.AdminPlanRequestDto;
import com.example.TenantEase.dto.AdminPlanResponseDto;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.service.AdminSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminSubscriptionController {

    private final AdminSubscriptionService adminService;

    @PostMapping("/plans")
    public ResponseEntity<Message<AdminPlanResponseDto>> createPlan(@RequestBody AdminPlanRequestDto request) {
        Message<AdminPlanResponseDto> response = adminService.createPlan(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/plans/{id}")
    public ResponseEntity<Message<AdminPlanResponseDto>> updatePlan(@PathVariable Long id, @RequestBody AdminPlanRequestDto request) {
        Message<AdminPlanResponseDto> response = adminService.updatePlan(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PatchMapping("/plans/{id}/activate")
    public ResponseEntity<Message<String>> activatePlan(@PathVariable Long id) {
        Message<String> response = adminService.activatePlan(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PatchMapping("/plans/{id}/deactivate")
    public ResponseEntity<Message<String>> deactivatePlan(@PathVariable Long id) {
        Message<String> response = adminService.deactivatePlan(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/plans")
    public ResponseEntity<Message<List<AdminPlanResponseDto>>> getAllPlans() {
        Message<List<AdminPlanResponseDto>> response = adminService.getAllPlans();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<Message<Object>> getAllSubscriptions() {
        Message<Object> response = adminService.getAllSubscriptions();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/payments")
    public ResponseEntity<Message<Object>> getAllPayments() {
        Message<Object> response = adminService.getAllPayments();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/revenue")
    public ResponseEntity<Message<Object>> getRevenueAnalytics() {
        Message<Object> response = adminService.getRevenueAnalytics();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

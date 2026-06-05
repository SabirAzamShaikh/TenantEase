package com.example.TenantEase.controller;

import com.example.TenantEase.dto.AdminPlanResponseDto;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.SubscriptionHistoryResponseDto;
import com.example.TenantEase.dto.UserSubscriptionResponseDto;
import com.example.TenantEase.jwt.JwtUtil;
import com.example.TenantEase.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final JwtUtil jwtUtil;

    private String getEmailFromToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return jwtUtil.extractUsername(token.substring(7));
        }
        throw new RuntimeException("Invalid token");
    }

    @GetMapping("/subscription/current")
    public ResponseEntity<Message<UserSubscriptionResponseDto>> getCurrentSubscription(@RequestHeader("Authorization") String token) {
        String email = getEmailFromToken(token);
        Message<UserSubscriptionResponseDto> response = subscriptionService.getCurrentSubscription(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/subscription/history")
    public ResponseEntity<Message<List<SubscriptionHistoryResponseDto>>> getSubscriptionHistory(@RequestHeader("Authorization") String token) {
        String email = getEmailFromToken(token);
        Message<List<SubscriptionHistoryResponseDto>> response = subscriptionService.getSubscriptionHistory(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/subscription/plans")
    public ResponseEntity<Message<List<AdminPlanResponseDto>>> getAvailablePlans() {
        Message<List<AdminPlanResponseDto>> response = subscriptionService.getAvailablePlans();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/subscription/upgrade")
    public ResponseEntity<Message<String>> upgradeSubscription(@RequestHeader("Authorization") String token, @RequestParam Long planId) {
        String email = getEmailFromToken(token);
        Message<String> response = subscriptionService.initiateUpgrade(email, planId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/subscription/cancel")
    public ResponseEntity<Message<String>> cancelSubscription(@RequestHeader("Authorization") String token) {
        String email = getEmailFromToken(token);
        Message<String> response = subscriptionService.cancelSubscription(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/subscription/status")
    public ResponseEntity<Message<UserSubscriptionResponseDto>> getSubscriptionStatus(@RequestHeader("Authorization") String token) {
        // Equivalent to current
        return getCurrentSubscription(token);
    }

    @GetMapping("/payment/history")
    public ResponseEntity<Message<Object>> getPaymentHistory(@RequestHeader("Authorization") String token) {
        String email = getEmailFromToken(token);
        Message<Object> response = subscriptionService.getPaymentHistory(email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

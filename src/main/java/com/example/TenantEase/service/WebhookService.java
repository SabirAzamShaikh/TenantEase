package com.example.TenantEase.service;

public interface WebhookService {
    void handleRazorpayWebhook(String eventType, String razorpaySubscriptionId);
}

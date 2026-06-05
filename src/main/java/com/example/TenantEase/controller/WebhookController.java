package com.example.TenantEase.controller;

import com.example.TenantEase.service.WebhookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {
    
    private final WebhookService webhookService;
    private final ObjectMapper objectMapper;

    @PostMapping("/razorpay")
    public ResponseEntity<String> handleRazorpayWebhook(@RequestBody String payload) {
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            String eventType = jsonNode.get("event").asText();
            String subscriptionId = jsonNode.path("payload").path("subscription").path("entity").path("id").asText();
            
            // In case the structure is simple
            if (subscriptionId == null || subscriptionId.isEmpty()) {
                subscriptionId = jsonNode.get("razorpaySubscriptionId").asText(); // fallback for dummy payload testing
            }

            webhookService.handleRazorpayWebhook(eventType, subscriptionId);

            return ResponseEntity.ok("Webhook processed successfully");
        } catch (Exception e) {
            log.error("Webhook error: ", e);
            return ResponseEntity.badRequest().body("Webhook processing failed: " + e.getMessage());
        }
    }
}

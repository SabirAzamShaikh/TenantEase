package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.PaymentTransactionRepository;
import com.example.TenantEase.Repository.SubscriptionHistoryRepository;
import com.example.TenantEase.Repository.UserSubscriptionRepository;
import com.example.TenantEase.enums.PaymentStatus;
import com.example.TenantEase.enums.SubscriptionAction;
import com.example.TenantEase.enums.SubscriptionStatus;
import com.example.TenantEase.model.PaymentTransaction;
import com.example.TenantEase.model.SubscriptionHistory;
import com.example.TenantEase.model.UserSubscription;
import com.example.TenantEase.service.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhookServiceImpl implements WebhookService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final SubscriptionHistoryRepository historyRepository;

    @Override
    @Transactional
    public void handleRazorpayWebhook(String eventType, String razorpaySubscriptionId) {
        log.info("DUMMY MODE: Skipping signature verification");
        log.info("Received webhook event: {} for subscription: {}", eventType, razorpaySubscriptionId);

        UserSubscription pendingSub = userSubscriptionRepository.findByRazorpaySubscriptionId(razorpaySubscriptionId)
                .orElse(null);

        if (pendingSub == null) {
            log.warn("Subscription not found for ID: {}", razorpaySubscriptionId);
            return;
        }

        switch (eventType) {
            case "subscription.activated":
            case "subscription.charged":
                // 1. Expire old active subscription
                userSubscriptionRepository.findByUser_UserIdAndStatus(pendingSub.getUser().getUserId(), SubscriptionStatus.ACTIVE)
                        .ifPresent(oldSub -> {
                            oldSub.setStatus(SubscriptionStatus.EXPIRED);
                            oldSub.setEndDate(LocalDateTime.now());
                            userSubscriptionRepository.save(oldSub);
                        });

                // 2. Activate new subscription
                pendingSub.setStatus(SubscriptionStatus.ACTIVE);
                pendingSub.setActivatedAt(LocalDateTime.now());
                pendingSub.setStartDate(LocalDateTime.now());
                userSubscriptionRepository.save(pendingSub);

                // 3. Create payment transaction
                PaymentTransaction payment = new PaymentTransaction();
                payment.setUser(pendingSub.getUser());
                payment.setUserSubscription(pendingSub);
                payment.setAmount(pendingSub.getSubscriptionPlan().getPrice());
                payment.setPaymentStatus(PaymentStatus.SUCCESS);
                payment.setRazorpayPaymentId("DUMMY_PAY_" + UUID.randomUUID().toString());
                payment.setRazorpaySubscriptionId(razorpaySubscriptionId);
                payment.setTransactionDate(LocalDateTime.now());
                payment.setCreatedAt(LocalDateTime.now());
                paymentTransactionRepository.save(payment);

                // 4. Create history record
                SubscriptionHistory history = new SubscriptionHistory();
                history.setUser(pendingSub.getUser());
                history.setNewPlan(pendingSub.getSubscriptionPlan());
                history.setActionType(SubscriptionAction.UPGRADED);
               history.setActionDate(LocalDateTime.now());
                history.setAmountPaid(pendingSub.getSubscriptionPlan().getPrice());
                history.setRemarks("Upgraded via webhook");
                historyRepository.save(history);
                break;

            case "payment.failed":
                pendingSub.setStatus(SubscriptionStatus.FAILED);
                userSubscriptionRepository.save(pendingSub);

                PaymentTransaction failedPayment = new PaymentTransaction();
                failedPayment.setUser(pendingSub.getUser());
                failedPayment.setUserSubscription(pendingSub);
                failedPayment.setAmount(pendingSub.getSubscriptionPlan().getPrice());
                failedPayment.setPaymentStatus(PaymentStatus.FAILED);
                failedPayment.setRazorpaySubscriptionId(razorpaySubscriptionId);
                failedPayment.setTransactionDate(LocalDateTime.now());
               failedPayment.setCreatedAt(LocalDateTime.now());
                paymentTransactionRepository.save(failedPayment);
                
                SubscriptionHistory failedHistory = new SubscriptionHistory();
                failedHistory.setUser(pendingSub.getUser());
                failedHistory.setNewPlan(pendingSub.getSubscriptionPlan());
                failedHistory.setActionType(SubscriptionAction.CANCELLED);
                failedHistory.setRemarks("Payment failed");
             failedHistory.setActionDate(LocalDateTime.now());
                historyRepository.save(failedHistory);
                break;

            case "subscription.cancelled":
                pendingSub.setStatus(SubscriptionStatus.CANCELLED);
                pendingSub.setCancelledAt(LocalDateTime.now());
                userSubscriptionRepository.save(pendingSub);

                SubscriptionHistory cancelHistory = new SubscriptionHistory();
                cancelHistory.setUser(pendingSub.getUser());
                cancelHistory.setOldPlan(pendingSub.getSubscriptionPlan());
                cancelHistory.setActionType(SubscriptionAction.CANCELLED);
                cancelHistory.setRemarks("Cancelled via webhook");
                cancelHistory.setActionDate(LocalDateTime.now());

                historyRepository.save(cancelHistory);
                break;

            default:
                log.info("Unhandled event type: {}", eventType);
        }
    }
}

package com.example.TenantEase.dto;

import com.example.TenantEase.enums.PaymentStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentTransactionResponseDto {
    private Long id;
    private Long amount;
    private String currency;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private PaymentStatus paymentStatus;
    private String paymentMethod;
    private LocalDateTime transactionDate;
}

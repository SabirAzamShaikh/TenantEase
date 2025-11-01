package com.example.TenantEase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Entity
@Data
@Accessors(chain = true)
public class TenantRent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;

    private int monthNumber;   // 1-12
    private int year;          // 2024, 2025...

    private Long dueOfThisMonth;  // Expected rent
    private boolean isPaid;       // Status

    private LocalDate dueDate;    // e.g., 5th of the month
    private LocalDate paidDate;   // When rent was actually paid

    private String paymentMode;   // CASH, UPI, CARD, BANK_TRANSFER
    //private String transactionId; // Bank txn ID / Receipt no.
    private String remarks;       // Free text

}

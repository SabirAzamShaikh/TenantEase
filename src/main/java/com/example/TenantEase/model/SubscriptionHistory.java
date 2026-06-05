package com.example.TenantEase.model;

import com.example.TenantEase.enums.SubscriptionAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_plan_id")
    private SubscriptionPlan oldPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_plan_id")
    private SubscriptionPlan newPlan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionAction actionType;

    private Long amountPaid;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime actionDate;

    private String remarks;
}

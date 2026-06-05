package com.example.TenantEase.Repository;

import com.example.TenantEase.model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    Optional<UserSubscription> findByRazorpaySubscriptionId(String razorpaySubscriptionId);
    Optional<UserSubscription> findByUser_UserIdAndStatus(int userId, com.example.TenantEase.enums.SubscriptionStatus status);
}

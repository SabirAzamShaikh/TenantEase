package com.example.TenantEase.aspect;

import com.example.TenantEase.Repository.PlanUsageRepository;
import com.example.TenantEase.Repository.UserRepository;
import com.example.TenantEase.Repository.UserSubscriptionRepository;
import com.example.TenantEase.enums.ResourceType;
import com.example.TenantEase.enums.SubscriptionStatus;
import com.example.TenantEase.exception.PlanLimitExceededException;
import com.example.TenantEase.jwt.JwtUtil;
import com.example.TenantEase.model.PlanUsage;
import com.example.TenantEase.model.User;
import com.example.TenantEase.model.UserSubscription;
import com.example.TenantEase.util.CheckPlanLimit;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class PlanLimitAspect {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final PlanUsageRepository planUsageRepository;

    @Before("@annotation(com.example.TenantEase.util.CheckPlanLimit)")
    public void checkPlanLimit(JoinPoint joinPoint) {
        String token = jwtUtil.extractTokenFromRequest();
        if (token == null) {
            throw new RuntimeException("Authorization token missing");
        }
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CheckPlanLimit checkPlanLimit = method.getAnnotation(CheckPlanLimit.class);
        ResourceType resourceType = checkPlanLimit.resource();

        UserSubscription activeSubscription = userSubscriptionRepository
                .findByUser_UserIdAndStatus(user.getUserId(), SubscriptionStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("No active subscription found"));

        PlanUsage planUsage = planUsageRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Plan usage tracking not found"));

        int limit = -1;
        int currentUsage = 0;

        switch (resourceType) {
            case PROPERTY:
                limit = activeSubscription.getSubscriptionPlan().getMaxProperties().intValue();
                currentUsage = planUsage.getPropertyCount();
                break;
            case ROOM:
                limit = activeSubscription.getSubscriptionPlan().getMaxRooms().intValue();
                currentUsage = planUsage.getRoomCount();
                break;
            case TENANT:
                limit = activeSubscription.getSubscriptionPlan().getMaxTenants().intValue();
                currentUsage = planUsage.getTenantCount();
                break;
        }

        if (limit != -1 && currentUsage >= limit) {
            throw new PlanLimitExceededException(
                    resourceType.name(),
                    currentUsage,
                    limit
            );
        }
    }
}

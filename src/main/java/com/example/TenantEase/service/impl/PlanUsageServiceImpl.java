package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.PlanUsageRepository;
import com.example.TenantEase.enums.ResourceType;
import com.example.TenantEase.exception.ResourceNotFoundException;
import com.example.TenantEase.model.PlanUsage;
import com.example.TenantEase.service.PlanUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanUsageServiceImpl implements PlanUsageService {

    private final PlanUsageRepository planUsageRepository;

    @Override
    @Transactional
    public void incrementUsage(int userId, ResourceType resourceType) {
        PlanUsage usage = planUsageRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usage tracking not found for user: " + userId));

        switch (resourceType) {
            case PROPERTY:
                usage.setPropertyCount(usage.getPropertyCount() + 1);
                break;
            case ROOM:
                usage.setRoomCount(usage.getRoomCount() + 1);
                break;
            case TENANT:
                usage.setTenantCount(usage.getTenantCount() + 1);
                break;
        }
        planUsageRepository.save(usage);
    }

    @Override
    @Transactional
    public void decrementUsage(int userId, ResourceType resourceType) {
        PlanUsage usage = planUsageRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usage tracking not found for user: " + userId));

        switch (resourceType) {
            case PROPERTY:
                if (usage.getPropertyCount() > 0) usage.setPropertyCount(usage.getPropertyCount() - 1);
                break;
            case ROOM:
                if (usage.getRoomCount() > 0) usage.setRoomCount(usage.getRoomCount() - 1);
                break;
            case TENANT:
                if (usage.getTenantCount() > 0) usage.setTenantCount(usage.getTenantCount() - 1);
                break;
        }
        planUsageRepository.save(usage);
    }
}

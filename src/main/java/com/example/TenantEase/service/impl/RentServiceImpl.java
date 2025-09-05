package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.TenantRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.service.RentService;

public class RentServiceImpl implements RentService {
private final TenantRepository tenantRepository;

    public RentServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Message<?> addRentDoneByTenant(int tenantId, boolean isPaid) {
        return null;
    }
}

package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;

public interface RentService {
    Message<?> addRentDoneByTenant(int tenantId, boolean isPaid);
}

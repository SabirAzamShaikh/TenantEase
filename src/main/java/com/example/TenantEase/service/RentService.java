package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TenantRentRequestDto;
import com.example.TenantEase.model.TenantRent;

import java.util.List;

public interface RentService {
    Message<?> addRentDoneByTenant(TenantRentRequestDto rentRequestDto);

    Message<List<TenantRent>> getRentDetailsByTenantId(long tenantId);
}

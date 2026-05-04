package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;

import java.util.List;

public interface TenantService {

    Message<TenantResponseDto> addTenant(TenantRequestDto tenant, Long roomId);
    Message<List<TenantResponseDto>> getAllTenant();

    //    @Cacheable(value = "tenant")
    Message<List<TenantResponseDto>> getAllTenantByOwner(String ownerName);

    Message<TenantResponseDto> getTenantById(long id);

    Message<TenantResponseDto> getTenantByEmail(String email);
}

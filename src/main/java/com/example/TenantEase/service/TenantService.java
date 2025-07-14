package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;

import java.util.List;

public interface TenantService {

    Message<TenantResponseDto> addTenant(TenantRequestDto tenant);
    Message<List<TenantResponseDto>> getAllTenant();

    Message<TenantResponseDto> getTenantById(long id);
}

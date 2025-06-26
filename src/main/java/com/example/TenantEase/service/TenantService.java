package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;

public interface TenantService {

    Message<TenantResponseDto> addTenant(TenantRequestDto tenant);
}

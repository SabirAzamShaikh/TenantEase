package com.example.TenantEase.mapper;

import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;
import com.example.TenantEase.model.Tenant;

public interface TenantMapper {
    Tenant requestDtoToEntity(TenantRequestDto requestDto);

    TenantResponseDto EntityToResponseDto(Tenant tenant);
}

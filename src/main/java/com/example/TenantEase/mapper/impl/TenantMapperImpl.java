package com.example.TenantEase.mapper.impl;

import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;
import com.example.TenantEase.mapper.TenantMapper;
import com.example.TenantEase.model.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantMapperImpl implements TenantMapper {
@Override
public  Tenant requestDtoToEntity(TenantRequestDto requestDto){
    return new Tenant().setEmail(requestDto.getEmail()).setTenant(true).setName(requestDto.getName())
            .setPhoneNumber(requestDto.getPhoneNumber()).setAdharNumber(requestDto.getAdharNumber())
            .setDepositeAmount(requestDto.getDepositeAmount());
}
@Override
public TenantResponseDto EntityToResponseDto(Tenant tenant){
    return new TenantResponseDto().setTenantId(tenant.getTenantId()).setRoomNumber(tenant.getRoomNumber())
            .setIstenant(tenant.isTenant()).setDepositeAmount(tenant.getDepositeAmount()).setAdharNumber(tenant.getAdharNumber())
            .setPhoneNumber(tenant.getPhoneNumber()).setEmail(tenant.getEmail()).setName(tenant.getName());
}
}

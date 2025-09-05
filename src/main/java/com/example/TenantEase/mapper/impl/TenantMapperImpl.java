package com.example.TenantEase.mapper.impl;

import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;
import com.example.TenantEase.mapper.TenantMapper;
import com.example.TenantEase.model.Tenant;
import com.example.TenantEase.model.TenantRent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TenantMapperImpl implements TenantMapper {
    @Override
    public Tenant requestDtoToEntity(TenantRequestDto requestDto) {
        TenantRent rent = new TenantRent();
        rent.setMonthNumber(LocalDate.now().getMonthValue()).setDueOfThisMonth(requestDto.getTenantRent());
        return new Tenant().setEmail(requestDto.getEmail()).setTenant(true).setName(requestDto.getName())
                .setPhoneNumber(requestDto.getPhoneNumber()).setAdharNumber(requestDto.getAdharNumber()).setRents(List.of(rent))
                .setDepositeAmount(requestDto.getDepositeAmount()).setCreateTime(LocalDate.now()).setTotalStayMonth(0).setRentAmount(requestDto.getTenantRent());
    }

    @Override
    public TenantResponseDto EntityToResponseDto(Tenant tenant) {
        return new TenantResponseDto().setTenantId(tenant.getTenantId()).setRoomNumber(tenant.getRoomNumber())
                .setIstenant(tenant.isTenant()).setDepositeAmount(tenant.getDepositeAmount()).setAdharNumber(tenant.getAdharNumber())
                .setPhoneNumber(tenant.getPhoneNumber()).setEmail(tenant.getEmail()).setName(tenant.getName());
    }
}

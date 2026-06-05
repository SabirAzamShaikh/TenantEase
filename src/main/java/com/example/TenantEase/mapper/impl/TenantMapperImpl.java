package com.example.TenantEase.mapper.impl;

import com.example.TenantEase.Repository.TenantRentRepository;
import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;
import com.example.TenantEase.jwt.JwtUtil;
import com.example.TenantEase.mapper.TenantMapper;
import com.example.TenantEase.model.Tenant;
import com.example.TenantEase.model.TenantRent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TenantMapperImpl implements TenantMapper {
    private final TenantRentRepository rentRepository;
    private final JwtUtil util;

    public TenantMapperImpl(TenantRentRepository rentRepository, JwtUtil util) {
        this.rentRepository = rentRepository;
        this.util = util;
    }

    @Override
    public Tenant requestDtoToEntity(TenantRequestDto requestDto) {
        // Extract username fresh for each request
        String token = util.extractTokenFromRequest();
        String username = (token != null) ? util.extractUsername(token) : null;
//This is for Actual Production For Same month
//        TenantRent rent = new TenantRent().setMonthNumber(LocalDate.now().getMonthValue()).setDueOfThisMonth(requestDto.getTenantRent()).setPaid(false).setYear(LocalDate.now().getYear())
//                .setDueDate(LocalDate.now().withDayOfMonth(requestDto.getPaymentDay()));
        LocalDate today = LocalDate.now();

// For testing, let's backdate to previous month
        LocalDate lastMonthDate = today.minusMonths(1);

// Clamp the day (so you don't get DateTimeException for shorter months)
        int safeDay = Math.min(requestDto.getRentPaymentDay(), lastMonthDate.lengthOfMonth());

        TenantRent rent = new TenantRent().setMonthNumber(lastMonthDate.getMonthValue()).setYear(lastMonthDate.getYear()).setDueOfThisMonth(requestDto.getTenantRent()).setPaid(false).setDueDate(lastMonthDate.withDayOfMonth(safeDay));
        TenantRent savedRent = rentRepository.save(rent);

        return new Tenant().setEmail(requestDto.getEmail()).setTenant(true).setName(requestDto.getName()).setPhoneNumber(requestDto.getPhoneNumber()).setAdharNumber(requestDto.getAdharNumber()).setCreatedBy(username) // ✅ token-based username
                .setRents(List.of(savedRent)).setDepositeAmount(requestDto.getDepositeAmount()).setCreateTime(LocalDate.now()).setTotalStayMonth(0).setRentAmount(requestDto.getTenantRent()).setRentPaymentDay(requestDto.getRentPaymentDay());
    }


    @Override
    public TenantResponseDto EntityToResponseDto(Tenant tenant) {
        return new TenantResponseDto().setTenantId(tenant.getTenantId()).setRoomNumber(tenant.getRoomNumber()).setIstenant(tenant.isTenant()).setDepositeAmount(tenant.getDepositeAmount()).setAdharNumber(tenant.getAdharNumber()).setPhoneNumber(tenant.getPhoneNumber()).setEmail(tenant.getEmail()).setName(tenant.getName()).setCreatedDate(tenant.getCreateTime()).setRents(tenant.getRents()).setRentAmount(tenant.getRentAmount()).setRentPaymentDay(tenant.getRentPaymentDay());
    }
}

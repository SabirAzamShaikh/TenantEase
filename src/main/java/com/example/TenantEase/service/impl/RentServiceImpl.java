package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.TenantRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TenantRentRequestDto;
import com.example.TenantEase.model.Tenant;
import com.example.TenantEase.model.TenantRent;
import com.example.TenantEase.service.RentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RentServiceImpl implements RentService {
    private final TenantRepository tenantRepository;

    public RentServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Message<?> addRentDoneByTenant(TenantRentRequestDto rentRequestDto) {
        Message<?> message = new Message<>();
        try {
            log.info("In RentServiceImpl addRentDoneByTenant with Request Dto {}", rentRequestDto);
            Tenant tenant = tenantRepository.findById(rentRequestDto.getTenantId()).orElse(null);
            if (tenant == null) {
                message.setResponseMessage("Tenant Not Found With ID " + rentRequestDto.getTenantId());
                message.setStatus(HttpStatus.NOT_FOUND);
                return message;
            }
            if (rentRequestDto.getYear() < tenant.getCreateTime().getYear()) {
                message.setResponseMessage("Tenant has Come After " + rentRequestDto.getYear());
                message.setStatus(HttpStatus.BAD_REQUEST);
                return message;
            }
            if (rentRequestDto.getMonthNumber() < tenant.getCreateTime().getMonthValue()) {
                message.setResponseMessage("Tenant has Come After " + rentRequestDto.getMonthNumber());
                message.setStatus(HttpStatus.BAD_REQUEST);
                return message;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Message<List<TenantRent>> getRentDetailsByTenantId(long tenantId) {
        Message<List<TenantRent>> message = new Message<>();
        try {
            log.info("In RentServiceImpl GetRentDetailsByTenantId with Tenant ID {}", tenantId);
            Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
            if (tenant == null) {
                message.setResponseMessage("Tenant Not Found With ID " + tenantId);
                message.setStatus(HttpStatus.NOT_FOUND);
                return message;
            }
            List<TenantRent> ls = tenant.getRents();
            message.setStatus(HttpStatus.OK);
            message.setData(ls);
            message.setResponseMessage("Rent Details Fetched As per The Tenant Id");
            return message;
        } catch (Exception e) {
            log.info("In RentServiceImpl GetRentDetailsByTenantId Get An Error {} ", e.getMessage());
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("AN Error Occurs While Fetching Rent details ");
            return message;
        }
    }
}

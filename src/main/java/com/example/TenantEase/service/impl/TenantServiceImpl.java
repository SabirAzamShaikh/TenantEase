package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.TenantRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TenantRequestDto;
import com.example.TenantEase.dto.TenantResponseDto;
import com.example.TenantEase.mapper.TenantMapper;
import com.example.TenantEase.model.Tenant;
import com.example.TenantEase.service.TenantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class TenantServiceImpl implements TenantService {
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    public TenantServiceImpl(TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    @Override
//    @CacheEvict(value = "tenant", allEntries = true)
    public Message<TenantResponseDto> addTenant(TenantRequestDto tenant) {
        Message<TenantResponseDto> message = new Message<>();
        try {
            Tenant savedTenant = tenantRepository.save(tenantMapper.requestDtoToEntity(tenant));
            log.info("Saved Tenant Details {}", savedTenant);
            message.setResponseMessage("Tenant Added SuccessFully");
            message.setStatus(HttpStatus.CREATED);
            message.setData(tenantMapper.EntityToResponseDto(savedTenant));
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Some Error Occurs While Saving the Tenant");
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }

    @Override
//    @Cacheable(value = "tenant")
    public Message<List<TenantResponseDto>> getAllTenant() {
        Message<List<TenantResponseDto>> message = new Message<>();
        try {
            List<Tenant> ls = tenantRepository.findAll();
            List<TenantResponseDto> response = ls.stream().map(tenantMapper::EntityToResponseDto).toList();
            message.setData(response);
            message.setResponseMessage("Tenant Response Data Fetch SuccessFully");
            message.setStatus(HttpStatus.OK);
            return message;
        } catch (Exception e) {
            message.setResponseMessage("Error Occurs While Fetching Data in getAllTenant() in TenantServiceImpl");
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return message;
        }
    }

    @Override
    public Message<TenantResponseDto> getTenantById(long id) {
        Message<TenantResponseDto> message = new Message<>();
        try {
            Tenant tenant = tenantRepository.findById(id).orElse(null);
            if (tenant != null) {
                message.setStatus(HttpStatus.OK);
                message.setResponseMessage("Tenant Find SuccessFully");
                message.setData(tenantMapper.EntityToResponseDto(tenant));
                return message;
            }
            message.setStatus(HttpStatus.NOT_FOUND);
            message.setResponseMessage("Tenant Not Find With Id :" + id);
            return message;
        } catch (Exception e) {
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setResponseMessage("Error Occurs At getTenantById() in TenantServiceImpl");
            return message;
        }
    }
}

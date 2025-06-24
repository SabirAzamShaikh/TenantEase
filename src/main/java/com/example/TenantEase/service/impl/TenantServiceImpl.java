package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.TenantRepository;
import com.example.TenantEase.dto.Message;
import com.example.TenantEase.model.Tenant;
import com.example.TenantEase.service.TenantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TenantServiceImpl implements TenantService {
private final TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Message<Tenant> addTenant(Tenant tenant) {
Message<Tenant> message=new Message<>();
     try{
Tenant savedTenant=tenantRepository.save(tenant);
log.info("Saved Tenant Details {}",savedTenant);
message.setResponseMessage("Tenant Added SuccessFully");
message.setStatus(HttpStatus.CREATED);
message.setData(savedTenant);
return message;
     } catch (Exception e) {
         message.setResponseMessage("Some Error Occurs While Saving the Tenant");
         message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
         return message;
     }
    }
}

package com.example.TenantEase.service;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.model.Tenant;

public interface TenantService {

    Message<Tenant> addTenant(Tenant tenant);
}

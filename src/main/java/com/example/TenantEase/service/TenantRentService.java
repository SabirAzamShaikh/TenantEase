package com.example.TenantEase.service;

import org.springframework.web.bind.annotation.RequestParam;

public interface TenantRentService {

    public void addRentDoneByTenant(int tenantId, boolean isPaid);
}
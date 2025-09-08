package com.example.TenantEase.controller;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.dto.TenantRentRequestDto;
import com.example.TenantEase.model.TenantRent;
import com.example.TenantEase.service.RentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rent")
public class RentController {
    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PutMapping("/paymentDone")
    public Message<?> addRentDoneByTenant(@RequestBody @Valid TenantRentRequestDto rentRequestDto) {
        return rentService.addRentDoneByTenant(rentRequestDto);
    }

    @GetMapping("/getRentDetails")
    public Message<List<TenantRent>> getRentDetailsByTenantId(@RequestParam("tenantId") long tenantId) {
        return rentService.getRentDetailsByTenantId(tenantId);
    }

}

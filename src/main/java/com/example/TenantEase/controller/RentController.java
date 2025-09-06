package com.example.TenantEase.controller;

import com.example.TenantEase.dto.Message;
import com.example.TenantEase.service.RentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rent")
public class RentController {
private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PutMapping("/paymentDone")
public Message<?> addRentDoneByTenant(@RequestParam("tenantId") int tenantId, @RequestParam("isPaid") boolean isPaid){
return rentService.addRentDoneByTenant(tenantId, isPaid);
}
}

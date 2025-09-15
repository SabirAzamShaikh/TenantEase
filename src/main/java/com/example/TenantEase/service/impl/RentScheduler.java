package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.TenantRentRepository;
import com.example.TenantEase.Repository.TenantRepository;
import com.example.TenantEase.model.Tenant;
import com.example.TenantEase.model.TenantRent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentScheduler {

    private final TenantRepository tenantRepository;
    private final TenantRentRepository rentRepository;

    @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
    public void generateMonthlyRent() {
        int currentMonth = LocalDate.now().getMonthValue();

        List<Tenant> tenants = tenantRepository.findAll();

        for (Tenant tenant : tenants) {
            boolean alreadyExists = tenant.getRents().stream()
                    .anyMatch(rent -> rent.getMonthNumber() == currentMonth
                            && rent.getDueDate().getYear() == LocalDate.now().getYear());

            if (!alreadyExists) {
                TenantRent rent = new TenantRent()
                        .setMonthNumber(currentMonth)
                        .setDueOfThisMonth(tenant.getRentAmount())
                        .setPaid(false)
                        .setDueDate(LocalDate.now().withDayOfMonth(1)); // First day of month

                TenantRent savedRent = rentRepository.save(rent);

                tenant.getRents().add(savedRent);
                tenantRepository.save(tenant);

                System.out.println("Added new rent for tenant: " + tenant.getName());
            }
        }
    }
}


//package com.example.TenantEase.service.impl;
//
//import com.example.TenantEase.Repository.TenantRentRepository;
//import com.example.TenantEase.Repository.TenantRepository;
//import com.example.TenantEase.model.Tenant;
//import com.example.TenantEase.model.TenantRent;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class RentScheduler {
//
//    private final TenantRepository tenantRepository;
//    private final TenantRentRepository rentRepository;
//
//    // Daily at midnight:
//  //  @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
//    // For testing: run every 2 minutes (uncomment if needed)
//     @Scheduled(cron = "0 */5 * * * *")
//    @Transactional
//    public void generateMonthlyRent() {
//        LocalDate today = LocalDate.now();
//        int year = today.getYear();
//        List<Tenant> tenants = tenantRepository.findAll();
//
//        for (Tenant tenant : tenants) {
//            try {
//                // null safe rents list if you need to use it later
//                if (tenant.getRents() == null) {
//                    tenant.setRents(new ArrayList<>());
//                }
//
//                int paymentDay = tenant.getRentPaymentDay();
//                // clamp to month length to avoid DateTimeException
//                int safeDay = Math.max(1, Math.min(paymentDay, today.lengthOfMonth()));
//                LocalDate dueDate = today.withDayOfMonth(safeDay);
//                int monthNumber = dueDate.getMonthValue();
//
//                // Better: ask DB if a rent exists for this tenant/month/year
//                boolean exists = rentRepository.existsByTenant_TenantIdAndMonthNumberAndYear(tenant.getTenantId(), monthNumber, year);
//                if (exists) {
//                    log.info("Rent already exists for tenantId={} month={} year={}", tenant.getTenantId(), monthNumber, year);
//                    continue;
//                }
//
//                TenantRent rent = new TenantRent()
//                        .setMonthNumber(monthNumber)
//                        .setYear(year)
//                        .setDueOfThisMonth(tenant.getRentAmount())
//                        .setPaid(false)
//                        .setDueDate(dueDate);
//
//
//                TenantRent saved = rentRepository.save(rent);
//
//                // maintain in-memory association (optional)
//                tenant.getRents().add(saved);
//                tenantRepository.save(tenant);
//
//                log.info("Added new rent for tenant: {} (id={}) for {}/{}", tenant.getName(), tenant.getTenantId(), monthNumber, year);
//            } catch (DataIntegrityViolationException ex) {
//                // possible race condition with another instance inserting the same unique (tenant, month, year)
//                log.warn("Tried to insert duplicate rent for tenantId={} month={} year={} — skipping", tenant.getTenantId(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
//            } catch (Exception e) {
//                log.error("Failed to create rent for tenantId={}", tenant.getTenantId(), e);
//            }
//        }
//    }
//}
//

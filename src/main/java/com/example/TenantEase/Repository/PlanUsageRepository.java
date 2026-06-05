package com.example.TenantEase.Repository;

import com.example.TenantEase.model.PlanUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanUsageRepository extends JpaRepository<PlanUsage, Long> {
    Optional<PlanUsage> findByUser_UserId(int userId);
}

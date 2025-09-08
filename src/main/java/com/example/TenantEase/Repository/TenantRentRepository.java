package com.example.TenantEase.Repository;

import com.example.TenantEase.model.TenantRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRentRepository extends JpaRepository<TenantRent,Long> {
}

package com.example.TenantEase.Repository;

import com.example.TenantEase.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenant,Long> {

List<Tenant> findAllByCreatedBy(String createdBy);
}

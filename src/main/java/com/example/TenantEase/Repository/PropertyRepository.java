package com.example.TenantEase.Repository;

import com.example.TenantEase.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwnerName(String ownerName);

    Object countByOwnerName(String email);
}

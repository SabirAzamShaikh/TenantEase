package com.example.TenantEase.Repository;

import com.example.TenantEase.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
        Optional<ImageData> findByName(String name);








    }
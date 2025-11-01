package com.example.TenantEase.Repository;

import com.example.TenantEase.model.Property;
import com.example.TenantEase.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByProperty(Property property);
    List<Room> findByAvailable(boolean available);
    List<Room> findByStatus(String status);
}
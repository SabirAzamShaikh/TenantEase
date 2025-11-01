package com.example.TenantEase.service;

import com.example.TenantEase.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Room addRoom(Room room, Long propertyId);
    Room updateRoom(Long roomId, Room updatedRoom);
    void deleteRoom(Long roomId);
    List<Room> getAllRooms();
    Optional<Room> getRoomById(Long roomId);
    List<Room> getRoomsByProperty(Long propertyId);
}

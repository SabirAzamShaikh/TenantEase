package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.PropertyRepository;
import com.example.TenantEase.Repository.RoomRepository;
import com.example.TenantEase.Repository.UserRepository;
import com.example.TenantEase.jwt.JwtUtil;
import com.example.TenantEase.model.Property;
import com.example.TenantEase.model.Room;
import com.example.TenantEase.model.User;
import com.example.TenantEase.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final PropertyRepository propertyRepository;
    private final JwtUtil util;
    private final UserRepository userRepository;
    @Override
    public Room addRoom(Room room, Long propertyId) {
        Property property=propertyRepository.findById(propertyId).orElseThrow(()->new RuntimeException("Property Not Found with Id "+propertyId));
        String token=util.extractTokenFromRequest();
        String username= token!=null?util.extractUsername(token):null;
        User user=userRepository.findByEmail(username).orElseThrow(()->new RuntimeException("User Not Found with Username "+username));
        room.setCreatedAt(LocalDateTime.now());
        room.setProperty(property);
        // 3. Save room
        Room savedRoom = roomRepository.save(room);

        // 4. Initialize property’s room list if null
        if (property.getRooms() == null) {
            property.setRooms(new ArrayList<>());
        }

        // 5. Add room and save property (optional, if you maintain bidirectional consistency)
        property.getRooms().add(savedRoom);
        propertyRepository.save(property);

        // 6. Return the saved room
        return savedRoom;
    }

    @Override
    public Room updateRoom(Long roomId, Room updatedRoom) {
        return roomRepository.findById(roomId).map(existing -> {
            updatedRoom.setRoomId(existing.getRoomId());
            updatedRoom.setUpdatedAt(LocalDateTime.now());
            return roomRepository.save(updatedRoom);
        }).orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
    }

    @Override
    public void deleteRoom(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new RuntimeException("Room not found with id: " + roomId);
        }
        roomRepository.deleteById(roomId);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return roomRepository.findById(roomId);
    }

    @Override
    public List<Room> getRoomsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));
        return roomRepository.findByProperty(property);
    }
}

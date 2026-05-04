package com.example.TenantEase.service.impl;

import com.example.TenantEase.Repository.PropertyRepository;
import com.example.TenantEase.Repository.RoomRepository;
import com.example.TenantEase.Repository.UserRepository;
import com.example.TenantEase.dto.RoomRequestDTO;
import com.example.TenantEase.dto.RoomResponseDTO;
import com.example.TenantEase.jwt.JwtUtil;
import com.example.TenantEase.mapper.RoomMapper;
import com.example.TenantEase.model.Property;
import com.example.TenantEase.model.Room;
import com.example.TenantEase.model.User;
import com.example.TenantEase.service.RoomService;
import com.example.TenantEase.util.UtilHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final PropertyRepository propertyRepository;
    private final JwtUtil util;
    private final UtilHelper helper;
    private final UserRepository userRepository;

    @Override
    public RoomResponseDTO addRoom(RoomRequestDTO roomRequestDTO, Long propertyId) {
        try {
            Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RuntimeException("Property Not Found with Id " + propertyId));
            String token = util.extractTokenFromRequest();
            String username = token != null ? util.extractUsername(token) : null;
            User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User Not Found with Username " + username));

            Room room = roomMapper.toEntity(roomRequestDTO);
            List<String> roomImageUrl = helper.imageSaver(roomRequestDTO.getRoomImages());
            room.setRoomImagePath(roomImageUrl);
            room.setCreatedAt(LocalDateTime.now());
            room.setCreatedBy(username);
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
            return roomMapper.toResponseDTO(savedRoom);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RoomResponseDTO updateRoom(Long id, RoomRequestDTO roomRequestDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        roomMapper.updateEntityFromDTO(roomRequestDTO, room);
        Room updatedRoom = roomRepository.save(room);
        return roomMapper.toResponseDTO(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        roomRepository.delete(room);
    }

    @Override
    public List<RoomResponseDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoomResponseDTO> getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(roomMapper::toResponseDTO);
    }

    @Override
    public List<RoomResponseDTO> getRoomsByProperty(Long propertyId) {
        return roomRepository.findByPropertyPropertyId(propertyId).stream()
                .map(roomMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<RoomResponseDTO> getRoomsByOwner(String username) {
        return roomRepository.findByCreatedBy(username).stream().map(roomMapper::toResponseDTO).toList();

    }
}

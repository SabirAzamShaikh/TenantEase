package com.example.TenantEase.service;


import com.example.TenantEase.dto.RoomRequestDTO;
import com.example.TenantEase.dto.RoomResponseDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    RoomResponseDTO addRoom(RoomRequestDTO roomRequestDTO, Long propertyId);
    RoomResponseDTO updateRoom(Long id, RoomRequestDTO roomRequestDTO) throws IOException;
    void deleteRoom(Long id);
    List<RoomResponseDTO> getAllRooms();
    Optional<RoomResponseDTO> getRoomById(Long id);
    List<RoomResponseDTO> getRoomsByProperty(Long propertyId);

    List<RoomResponseDTO> getRoomsByOwner(String username);
}

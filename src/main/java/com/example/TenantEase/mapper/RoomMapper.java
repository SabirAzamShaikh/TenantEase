package com.example.TenantEase.mapper;

import com.example.TenantEase.dto.RoomRequestDTO;
import com.example.TenantEase.dto.RoomResponseDTO;
import com.example.TenantEase.model.Room;

public interface RoomMapper {
    public Room toEntity(RoomRequestDTO dto);
    public RoomResponseDTO toResponseDTO(Room room);
    public void updateEntityFromDTO(RoomRequestDTO dto, Room room);
}

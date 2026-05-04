package com.example.TenantEase.mapper.impl;


import com.example.TenantEase.dto.RoomRequestDTO;
import com.example.TenantEase.dto.RoomResponseDTO;
import com.example.TenantEase.mapper.RoomMapper;
import com.example.TenantEase.model.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapperImpl implements RoomMapper {

    public Room toEntity(RoomRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Room.builder()
                .roomNumber(dto.getRoomNumber())
                .description(dto.getDescription())
                .roomType(dto.getRoomType())
                .rentAmount(dto.getRentAmount())
                .depositAmount(dto.getDepositAmount())
                .available(dto.isAvailable())
                .furnishingType(dto.getFurnishingType())
                .occupancyLimit(dto.getOccupancyLimit())
                .floor(dto.getFloor())
                .attachedBath(dto.isAttachedBath())
                .amenities(dto.getAmenities())
                .status("available")
                .build();
    }

    public RoomResponseDTO toResponseDTO(Room room) {
        if (room == null) {
            return null;
        }

        return RoomResponseDTO.builder()
                .id(room.getRoomId())
                .roomNumber(room.getRoomNumber())
                .description(room.getDescription())
                .roomType(room.getRoomType())
                .rentAmount(room.getRentAmount())
                .depositAmount(room.getDepositAmount())
                .available(room.isAvailable())
                .furnishingType(room.getFurnishingType())
                .occupancyLimit(room.getOccupancyLimit())
                .floor(room.getFloor())
                .attachedBath(room.isAttachedBath())
                .amenities(room.getAmenities())
                .imageUrls(room.getRoomImagePath())
                .propertyId(room.getProperty() != null ? room.getProperty().getPropertyId(): null)
                .build();
    }

    public void updateEntityFromDTO(RoomRequestDTO dto, Room room) {
        if (dto == null) {
            return;
        }
        room.setRoomNumber(dto.getRoomNumber());
        room.setDescription(dto.getDescription());
        room.setRoomType(dto.getRoomType());
        room.setRentAmount(dto.getRentAmount());
        room.setDepositAmount(dto.getDepositAmount());
        room.setAvailable(dto.isAvailable());
        room.setFurnishingType(dto.getFurnishingType());
        room.setOccupancyLimit(dto.getOccupancyLimit());
        room.setFloor(dto.getFloor());
        room.setAttachedBath(dto.isAttachedBath());
        room.setAmenities(dto.getAmenities());
    }
}

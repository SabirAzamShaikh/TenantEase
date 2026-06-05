package com.example.TenantEase.controller;

import com.example.TenantEase.dto.RoomRequestDTO;
import com.example.TenantEase.dto.RoomResponseDTO;
import com.example.TenantEase.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping(value = "/addRoom", consumes = { "multipart/form-data" })
    public ResponseEntity<RoomResponseDTO> addRoom(@ModelAttribute RoomRequestDTO roomRequestDTO,
            @RequestParam Long propertyId) {
        return ResponseEntity.ok(roomService.addRoom(roomRequestDTO, propertyId));
    }

    @PutMapping(value = "/updateRoom", consumes = { "multipart/form-data" })
    public ResponseEntity<RoomResponseDTO> updateRoom(@RequestParam Long id,
            @ModelAttribute RoomRequestDTO updatedRoomDTO) throws IOException {
        return ResponseEntity.ok(roomService.updateRoom(id, updatedRoomDTO));
    }

    @DeleteMapping("/deleteRoom")
    public ResponseEntity<String> deleteRoom(@RequestParam Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Room deleted successfully.");
    }

    @GetMapping("/getAllRoom")
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/getById")
    public ResponseEntity<RoomResponseDTO> getRoomById(@RequestParam Long id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getByProperty")
    public ResponseEntity<List<RoomResponseDTO>> getRoomsByProperty(@RequestParam Long propertyId) {
        return ResponseEntity.ok(roomService.getRoomsByProperty(propertyId));
    }

    @GetMapping("/getByOwner")
    public ResponseEntity<List<RoomResponseDTO>> getRoomsByOwner(@RequestParam String username) {
        return ResponseEntity.ok(roomService.getRoomsByOwner(username));
    }

}

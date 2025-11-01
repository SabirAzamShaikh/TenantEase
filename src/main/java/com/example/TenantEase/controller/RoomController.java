package com.example.TenantEase.controller;

import com.example.TenantEase.model.Room;
import com.example.TenantEase.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/addRoom")
    public ResponseEntity<Room> addRoom(@RequestBody Room room,@RequestParam Long propertyId) {
        return ResponseEntity.ok(roomService.addRoom(room,propertyId));
    }

    @PutMapping("/updateRoom")
    public ResponseEntity<Room> updateRoom(@RequestParam Long id, @RequestBody Room updatedRoom) {
        return ResponseEntity.ok(roomService.updateRoom(id, updatedRoom));
    }

    @DeleteMapping("/deleteRoom")
    public ResponseEntity<String> deleteRoom(@RequestParam Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Room deleted successfully.");
    }

    @GetMapping("/getAllRoom")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/getById")
    public ResponseEntity<Room> getRoomById(@RequestParam Long id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getByProperty")
    public ResponseEntity<List<Room>> getRoomsByProperty(@RequestParam Long propertyId) {
        return ResponseEntity.ok(roomService.getRoomsByProperty(propertyId));
    }
}

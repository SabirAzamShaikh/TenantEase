package com.example.TenantEase.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Existing @MessageMapping (optional)

    // NEW TESTING API: send notification via WebSocket
//    @MessageMapping("/testNotify")
////	@SendTo("/topic/public")
//    public void testNotify(@Payload String msg) {
//        Notification n = Notification.builder().message(msg).externalMessage("External: " + msg)
//                .header("Test Notification").timestamp(new Timestamp(System.currentTimeMillis())).isRead(false).build();
//
//        Notification saved = notificationRepository.save(n);
//
//        // Broadcast the saved notification to all WebSocket clients
//        messagingTemplate.convertAndSend("/topic/public", saved);
//    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public String sendMessage(@Payload String notification) {
        return notification;
    }

//    @CrossOrigin(origins = "http://localhost:5173")
//    @GetMapping("/getNotification")
//    public ResponseEntity<List<Notification>> getAllNotification() {
//        List<Notification> ls = notificationRepository.findAll();
//        return ResponseEntity.status(HttpStatus.OK).body(ls);
//    }

}


package com.example.TenantEase.controller;

import com.example.TenantEase.service.impl.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public String sendNotification(
//            @RequestParam String email,
//            @RequestParam String phone,
            @RequestParam String subject,
          //  @RequestParam String html,
            @RequestParam String sms
    ) {
        return notificationService.sendNotification( subject, sms);
    }
}

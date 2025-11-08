package com.example.TenantEase.service.impl;

import com.notificationapi.NotificationApi;
import com.notificationapi.model.*;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationApi api;

    public NotificationService() {
        this.api = new NotificationApi(
                "gsdp9swtq776qdgupkp8y5y9tr", // Client ID
                "c45ftxu5jy53sqwe1ci6bsijey0psbil2ms98xnvxn563e148ta96c62nf" // Client Secret
        );
    }
    User user = new User("www.sabirazamshaikh313@gmail.com")
            .setEmail("www.sabirazamshaikh313@gmail.com")
            .setNumber("+918421682861");
    public String sendNotification(String subject, String smsMessage) {

        NotificationRequest request = new NotificationRequest("babaresidency", user)
                .setEmail(new EmailOptions()
                        .setSubject("Your verification code")//This is the Subject of the email
                        .setHtml("Your verification code is: 123456")//THe Actual Message or content of the mail
                )
                .setSms(new SmsOptions()
                        .setMessage("Hello, world!")//The Actual message which we will send through SMS
                );

        return api.send(request);
    }
}

package com.example.TenantEase.service.impl;

import com.example.TenantEase.model.Tenant;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        try{
            javaMailSender.send(email);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void sendEmail(MimeMessage mailMessage) {
        javaMailSender.send(mailMessage);

    }

//    @Async
//    public void sendBulkEmail(EmailDto dto) {
//        for (String recipient : dto.getEmaiList()) {
//            sendEmailToRecipient(recipient, dto.getSubject(), dto.getMessageString());
//        }
//    }

    @Async
    public void sendEmailToRecipient(String to, String subject, String messageBody) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(messageBody, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Replace with proper logging
        }
    }

    @Async
    public void sendEmailToTenant(String to, String subject, Tenant tenant) {
        try {
            log.debug("Sending Email to "+to);
            String messageBody = "<html>" +
                    "<body style='font-family:Arial, sans-serif;'>" +
                    "<p>Dear " + tenant.getName() + ",</p>" +

                    "<p>Welcome to <b>Sample Apartment</b>!<br>" +
                    "We are pleased to inform you that your registration has been completed successfully.</p>" +

                    "<h3>Registration Details:</h3>" +
                    "<ul>" +
                    "<li><b>Tenant ID:</b> " + tenant.getTenantId() + "</li>" +
                    "<li><b>Name:</b> " + tenant.getName() + "</li>" +
                    "<li><b>Email:</b> " + tenant.getEmail() + "</li>" +
                    "<li><b>Apartment/Flat No:</b> " + tenant.getRoomNumber()+ "</li>" +
                    "</ul>" +

                    "<p>You can now access your tenant portal to:</p>" +
                    "<ul>" +
                    "<li>View and pay rent online</li>" +
                    "<li>Track payment history</li>" +
                    "<li>Submit service requests</li>" +
                    "<li>Update your profile details</li>" +
                    "</ul>" +

                    "<p>If you have any questions or need assistance, feel free to reach out to our support team at " +
                    "<a href='mailto:support@sample.com'>support@sample.com</a> or call +91-XXXXXXXXXX.</p>" +

                    "<p>We’re excited to have you as part of our community!</p>" +

                    "<p>Warm regards,<br>" +
                    "<b>Sample Apartment Management Team</b></p>" +
                    "</body>" +
                    "</html>";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(messageBody, true); // HTML email

            javaMailSender.send(message);
            log.debug("Email Send SuccessFully to "+tenant.getEmail());
        } catch (Exception e) {
            log.error("Error occurred while sending email to tenant: {}", e.getMessage(), e);
        }
    }




}

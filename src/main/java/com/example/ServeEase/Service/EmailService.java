package com.example.ServeEase.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("pratik014pratik@gmail.com");

        try {
            javaMailSender.send(message);
            return true;
        } catch (MailException e) {
            return false;
 }
}
    public void sendWelcomeEmail(String email, String name) {
        String subject = "Welcome to ServeEase!";
        String text = "Hello " + name + ",\n\n" +
                "Welcome to ServeEase! We are excited to have you on board. " +
                "We are here to make your life easier. " +
                "Please let us know if you have any questions or concerns. " +
                "We are here to help you. " +
                "Thank you for choosing ServeEase. " +
                "Have a great day!";

        boolean mailSent = this.sendMail(email, subject, text);
        if (mailSent) {
            System.out.println("Welcome email sent successfully to " + email);
        } else {
            System.out.println("Failed to send welcome email to " + email);
}
}

    public void updateBookingStatus(String categoryName, String providerName, String seekerName, String status,
    String seekerEmail,String providerEmail
    ) {
        String subject = "Booking Status Update";
        String text = "Hello " + seekerName + ",\n\n" +
                "Your booking with " + providerName + " for " + categoryName + " has been updated. " +
                "The new status is: " + status + ". " +
                "Please let us know if you have any questions or concerns. " +
                "We are here to help you. " +
                "Thank you for choosing ServeEase. " +
                "Have a great day!";

        boolean mailSent = this.sendMail(seekerEmail, subject, text);

        String text2 = "Hello " + providerName + ",\n\n" +
                "The booking with " + seekerName + " for " + categoryName + " has been updated. " +
                "The new status is: " + status + ". " +
                "Please let us know if you have any questions or concerns. " +
                "We are here to help you. " +
                "Thank you for choosing ServeEase. " +
                "Have a great day!";
        boolean mailSent2 = this.sendMail(providerEmail, subject, text2);

        if (mailSent2) {
            System.out.println("Booking status update email sent successfully to " + providerEmail);
        } else {
            System.out.println("Failed to send booking status update email to " + providerEmail);
        }
        if (mailSent) {
            System.out.println("Booking status update email sent successfully to " + seekerEmail);
        } else {
            System.out.println("Failed to send booking status update email to " + seekerEmail);
        }

    }

    public void sendBookingEmail(String email, String newBookingRequest, String s) {

        boolean mailSent = this.sendMail(email, newBookingRequest, s);
        if (mailSent) {
            System.out.println("Booking email sent successfully to " + email);
        } else {
            System.out.println("Failed to send booking email to " + email);
        }
    }
}
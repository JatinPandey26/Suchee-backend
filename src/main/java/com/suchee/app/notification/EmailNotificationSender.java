package com.suchee.app.notification;

import com.suchee.app.entity.Notification;
import com.suchee.app.logging.Trace;
import com.suchee.app.validation.Validators.EmailValidator;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Implementation of NotificationSender to send notifications via email.
 */
@Component
public class EmailNotificationSender implements NotificationSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * Validates the notification for email sending.
     * Checks the sender's email and all recipient emails for correctness.
     *
     * @param notification the notification to validate
     * @return true if the sender and all recipients have valid emails; false otherwise
     */
    @Override
    public boolean validateNotification(Notification notification) {
        try {
            EmailValidator.validate(sender);

            // Validate each recipient email address
            Arrays.stream(notification.getRecipients())
                    .forEach(EmailValidator::validate);

            if (Trace.notification) {
                Trace.log("Validation successful for notification with subject: " + notification.getSubject());
            }

        } catch (Exception e) {
            if (Trace.notification) {
                Trace.log("Validation failed for notification with subject: " + notification.getSubject() +
                        ". Reason: " + e.getMessage());
            }
            return false;
        }
        return true;
    }

    /**
     * Sends the email notification.
     *
     * @param notification the notification to send
     * @return true if sending succeeded; false otherwise
     */
    @Override
    public boolean send(Notification notification) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setContent(notification.getMessage(), "text/html; charset=UTF-8");

            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(notification.getRecipients());
            helper.setSubject(notification.getSubject());
            helper.setFrom(sender);

            javaMailSender.send(message);

            if (Trace.notification) {
                Trace.log("Email sent successfully to " + String.join(", ", notification.getRecipients()) +
                        " with subject: " + notification.getSubject());
            }

        } catch (Exception e) {
            if (Trace.notification) {
                Trace.log("Failed to send email to " + String.join(", ", notification.getRecipients()) +
                        " with subject: " + notification.getSubject() + ". Reason: " + e.getMessage());
            }
            return false;
        }
        return true;
    }

    /**
     * Indicates whether this sender supports the given notification type.
     *
     * @param type the notification type to check
     * @return true if the type is EMAIL; false otherwise
     */
    @Override
    public boolean supports(NotificationType type) {
        return NotificationType.EMAIL.equals(type);
    }
}

package com.suchee.app.handler;

import com.suchee.app.dto.UserDTO;
import com.suchee.app.entity.UserAccount;
import com.suchee.app.enums.EventMessageType;
import com.suchee.app.logging.Trace;
import com.suchee.app.messaging.async.impl.UserCreationEvent;
import com.suchee.app.notification.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Async handler for user creation events.
 * Sends a welcome email notification to the newly created user.
 */
@RequiredArgsConstructor
@Component
public class UserCreationAsyncHandler implements AsyncHandler<UserCreationEvent> {

    private final ApplicationLinks applicationLinks;
    private final NotificationProcessor notificationProcessor;

    @Value("${suchee.app.name:Suchee}")
    private String applicationName;

    /**
     * Handles the UserCreationEvent by constructing a notification
     * and processing it through the NotificationProcessor.
     *
     * @param message the UserCreationEvent containing the new user's info
     */
    @Override
    public void doHandle(UserCreationEvent message) {

        UserDTO userDTO = message.getUserDTO();

        if (Trace.userCreation) {
            Trace.log("Starting processing UserCreationEvent for user ID: " + userDTO.getId());
        }

        Map<NotificationType, String> templateHashMap = new HashMap<>();
        templateHashMap.put(NotificationType.EMAIL, TemplatesRegistry.USER_CREATION_WELCOME_EMAIL.getTemplateName());

        Map<String, Object> userCreateMailData = new HashMap<>();
        userCreateMailData.put("user", userDTO);
        userCreateMailData.put("loginLink", applicationLinks.getApplicationBaseUrl());
        userCreateMailData.put("year", LocalDate.now().getYear());
        userCreateMailData.put("companyName", applicationName);

        Map<NotificationType, Map<String, Object>> templateData = new HashMap<>();
        templateData.put(NotificationType.EMAIL, userCreateMailData);

        Map<NotificationType, String> subjectMap = new HashMap<>();
        subjectMap.put(NotificationType.EMAIL, "Welcome to " + applicationName);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationTypes(Set.of(NotificationType.EMAIL))
                .recipients(new String[]{userDTO.getEmail()})
                .referenceId(UserAccount.getEntityName() + "-WELCOME" + userDTO.getId())
                .templateName(templateHashMap)
                .templateVariables(templateData)
                .subject(subjectMap)
                .build();

        this.notificationProcessor.doProcess(notificationRequest, false);

        if (Trace.userCreation) {
            Trace.log("Completed processing UserCreationEvent for user ID: " + userDTO.getId());
        }
    }

    @Override
    public EventMessageType getEventType() {
        return EventMessageType.USER_CREATED;
    }
}

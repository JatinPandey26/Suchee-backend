package com.suchee.app.notification;

import java.util.Map;
import java.util.Set;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class NotificationRequest  {

    @NonNull
    private final String[] recipients;  // mandatory

    @NonNull
    private final Set<NotificationType> notificationTypes; // mandatory

    private final String referenceId; // for MemberInvite - MEMBER_INVITE-id

    private final Map<NotificationType, String> templateNames; // templates per channel

    private final Map<NotificationType, Map<String, Object>> templateVariables; // variables per channel

    private final Map<NotificationType, String> subjects; // subjects per channel

    private final Map<NotificationType, String> messages;


    @Builder
    public NotificationRequest(@NonNull String[] recipients,
                               @NonNull Set<NotificationType> notificationTypes,
                               String referenceId,
                               Map<NotificationType, String> templateName,
                               Map<NotificationType, Map<String, Object>> templateVariables,
                               Map<NotificationType, String> subject,
                               Map<NotificationType, String> message) {
        this.recipients = recipients;
        this.notificationTypes = notificationTypes;
        this.referenceId=referenceId;
        this.templateNames = templateName;
        this.templateVariables = templateVariables;
        this.subjects = subject;
        this.messages = message;
    }



}



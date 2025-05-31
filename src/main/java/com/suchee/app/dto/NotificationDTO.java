package com.suchee.app.dto;

import com.suchee.app.enums.NotificationStatus;
import com.suchee.app.notification.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO extends AbstractPersistableDTO{

    private Long id;
    private String[] recipients;
    private NotificationType type;
    private String subject;
    private String message;
    private String template;
    private String referenceId;
    private NotificationStatus status;
    private String failureReason;
    private int retryCount;
    private LocalDateTime lastRetriedAt;

}

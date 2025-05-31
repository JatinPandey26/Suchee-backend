package com.suchee.app.notification;

import com.suchee.app.entity.Notification;

public interface NotificationSender {

    boolean validateNotification(Notification notification);

    boolean send(Notification notification);

    boolean supports(NotificationType type);
}

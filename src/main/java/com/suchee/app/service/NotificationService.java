package com.suchee.app.service;

import com.suchee.app.dto.NotificationDTO;
import com.suchee.app.entity.Notification;
import java.util.List;

public interface NotificationService {

    List<NotificationDTO> getByReferenceId(String referenceId);

    NotificationDTO save(Notification notification);

    NotificationDTO update(NotificationDTO notificationDTO);

}

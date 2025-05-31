package com.suchee.app.service.impl;

import com.suchee.app.dto.NotificationDTO;
import com.suchee.app.mapper.NotificationMapper;
import com.suchee.app.entity.Notification;
import com.suchee.app.repository.NotificationRepository;
import com.suchee.app.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationDTO> getByReferenceId(String referenceId) {
        return List.of();
    }

    @Override
    public NotificationDTO save(Notification notification) {


        Notification savedNotification = this.notificationRepository.save(notification);

        return this.notificationMapper.toDTO(savedNotification);
    }

    @Override
    public NotificationDTO update(NotificationDTO notificationDTO) {
        return null;
    }
}

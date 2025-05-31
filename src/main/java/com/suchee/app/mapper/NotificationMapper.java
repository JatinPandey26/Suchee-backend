package com.suchee.app.mapper;

import com.suchee.app.dto.NotificationDTO;
import com.suchee.app.entity.Notification;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "new", ignore = true)
    @Mapping(target = "lastUser", ignore = true)
    Notification toEntity(NotificationDTO notificationDTO);
    NotificationDTO toDTO(Notification notification);

    @Mapping(target = "new", ignore = true)
    @Mapping(target = "lastUser", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fillNotificationWithNotificationDto(NotificationDTO notificationDTO, @MappingTarget Notification notification);
}

package com.suchee.app.entity;

import com.suchee.app.core.entities.NonVersioned;
import com.suchee.app.core.types.converters.StringArrayConverter;
import com.suchee.app.enums.NotificationStatus;
import com.suchee.app.notification.NotificationType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Entity
public class Notification extends NonVersioned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StringArrayConverter.class)
    @Column(name = "recipients")
    private String[] recipients; // Comma-separated list of recipients

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;

    private String subject;

    @Lob
    private String message;

    private String template;

    private String referenceId; // Use to associate with other entities loosely (e.g., MemberInvitation ID)

    @Enumerated(EnumType.STRING)
    private NotificationStatus status; // SENT, FAILED, PENDING

    private String failureReason;

    private int retryCount;

    private LocalDateTime lastRetriedAt;


    @Override
    public Long getId() {
        return id;
    }
}

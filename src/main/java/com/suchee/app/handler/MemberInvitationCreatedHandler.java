package com.suchee.app.handler;

import com.suchee.app.dto.MemberInvitationDto;
import com.suchee.app.entity.MemberInvitation;
import com.suchee.app.enums.EventMessageType;
import com.suchee.app.enums.MemberInvitationStatus;
import com.suchee.app.logging.Trace;
import com.suchee.app.messaging.async.impl.MemberInvitationCreatedEvent;
import com.suchee.app.notification.*;
import com.suchee.app.service.MemberInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Handles MemberInvitationCreatedEvent by preparing and sending notification,
 * then updates invitation status.
 */
@Component
@RequiredArgsConstructor
public class MemberInvitationCreatedHandler implements AsyncHandler<MemberInvitationCreatedEvent> {

    private final NotificationProcessor notificationProcessor;
    private final MemberInvitationService memberInvitationService;
    private final ApplicationLinks applicationLinks;

    /**
     * Processes the MemberInvitationCreatedEvent.
     * Builds notification requests and sends notifications via NotificationProcessor.
     * Updates the invitation status to SENT after processing.
     *
     * @param message the MemberInvitationCreatedEvent containing invitation details
     */
    @Override
    public void doHandle(MemberInvitationCreatedEvent message) {
        MemberInvitationDto memberInvitationDto = message.getMemberInvitationDto();

        if (Trace.member) {
            Trace.log("Processing MemberInvitationCreatedEvent for invitation ID: " + memberInvitationDto.getId());
        }

        // Prepare templates map for notification types
        Map<NotificationType, String> templateHashMap = new HashMap<>();
        templateHashMap.put(NotificationType.EMAIL, TemplatesRegistry.MEMBER_INVITATION_EMAIL.getTemplateName());

        // Prepare template variables map
        Map<String, Object> memberInvitationMailData = new HashMap<>();
        memberInvitationMailData.put("teamName", memberInvitationDto.getTeam().getTeamName());
        memberInvitationMailData.put("invitationLink", applicationLinks.getApplicationBaseUrl());

        Map<NotificationType, Map<String, Object>> templateData = new HashMap<>();
        templateData.put(NotificationType.EMAIL, memberInvitationMailData);

        // Prepare subject map
        Map<NotificationType, String> subjectMap = new HashMap<>();
        subjectMap.put(NotificationType.EMAIL, "You are invited to : " + memberInvitationDto.getTeam().getTeamName());

        NotificationRequest memberInvitationNotificationRequest = NotificationRequest.builder()
                .recipients(new String[]{memberInvitationDto.getEmail()})
                .notificationTypes(Set.of(NotificationType.EMAIL))
                .templateName(templateHashMap)
                .templateVariables(templateData)
                .subject(subjectMap)
                .referenceId(MemberInvitation.getEntityName() + "-" + memberInvitationDto.getId())
                .build();

        this.notificationProcessor.doProcess(memberInvitationNotificationRequest, true);

        this.memberInvitationService.changeStatusOfInvitation(memberInvitationDto.getId(), MemberInvitationStatus.SENT);

        if (Trace.member) {
            Trace.log("Notification sent and status updated to SENT for invitation ID: " + memberInvitationDto.getId());
        }
    }

    /**
     * Returns the EventMessageType this handler supports.
     *
     * @return EventMessageType.MEMBER_INVITE_CREATED
     */
    @Override
    public EventMessageType getEventType() {
        return EventMessageType.MEMBER_INVITE_CREATED;
    }
}

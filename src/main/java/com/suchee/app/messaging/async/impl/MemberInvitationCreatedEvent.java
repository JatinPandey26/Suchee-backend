package com.suchee.app.messaging.async.impl;

import com.suchee.app.dto.MemberInvitationDto;
import com.suchee.app.enums.EventMessageType;
import com.suchee.app.messaging.async.AsyncEventPublishType;
import com.suchee.app.messaging.async.AsyncMessage;
import com.suchee.app.messaging.async.QueueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberInvitationCreatedEvent extends AsyncMessage<MemberInvitationDto> {

    private MemberInvitationDto memberInvitationDto;

    public MemberInvitationCreatedEvent(MemberInvitationDto memberInvitationDto,AsyncEventPublishType asyncEventPublishType) {
        super(asyncEventPublishType, QueueType.NOTIFICATION);
        this.memberInvitationDto=memberInvitationDto;
    }

    @Override
    public MemberInvitationDto getMessage() {
        return this.memberInvitationDto;
    }

    @Override
    public EventMessageType getType() {
        return EventMessageType.MEMBER_INVITE_CREATED;
    }


}

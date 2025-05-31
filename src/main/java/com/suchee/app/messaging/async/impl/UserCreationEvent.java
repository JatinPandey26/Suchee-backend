package com.suchee.app.messaging.async.impl;

import com.suchee.app.dto.MemberInvitationDto;
import com.suchee.app.dto.UserDTO;
import com.suchee.app.enums.EventMessageType;
import com.suchee.app.messaging.async.AsyncEventPublishType;
import com.suchee.app.messaging.async.AsyncMessage;
import com.suchee.app.messaging.async.QueueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreationEvent extends AsyncMessage<UserDTO> {

    private UserDTO userDTO;

    public UserCreationEvent(UserDTO userDTO, AsyncEventPublishType asyncEventPublishType) {
        super(asyncEventPublishType, QueueType.NOTIFICATION);
        this.userDTO=userDTO;
    }

    @Override
    public UserDTO getMessage() {
        return this.userDTO;
    }

    @Override
    public EventMessageType getType() {
        return EventMessageType.USER_CREATED;
    }
}

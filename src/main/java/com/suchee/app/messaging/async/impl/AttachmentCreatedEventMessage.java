package com.suchee.app.messaging.async.impl;

import com.suchee.app.dto.AttachmentDTO;
import com.suchee.app.dto.AttachmentUploadRequestDto;
import com.suchee.app.enums.EventMessageType;
import com.suchee.app.messaging.async.AsyncMessage;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttachmentCreatedEventMessage implements AsyncMessage<EventMessageType> {

    private final String eventId;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String source;
    private final AttachmentUploadRequestDto attachmentUploadRequestDto;

    public AttachmentCreatedEventMessage(String eventId, String source, AttachmentUploadRequestDto attachmentDto) {
        this.eventId = eventId;
        this.source = source;
        this.attachmentUploadRequestDto = attachmentDto;
    }

    @Override
    public EventMessageType getMessage() {
        return null;
    }

    @Override
    public EventMessageType getType() {
        return EventMessageType.ATTACHMENT_CREATE;
    }
}

package com.suchee.app.messaging.async.impl;

import com.suchee.app.enums.EventMessageType;
import com.suchee.app.logging.Trace;
import com.suchee.app.messaging.async.AsyncProcessor;
import com.suchee.app.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AttachmentCreationAsyncProcessor implements AsyncProcessor<AttachmentCreatedEventMessage> {

    @Autowired
    AttachmentService attachmentService;

    @Async
    @EventListener
    @Override
    public void process(AttachmentCreatedEventMessage event) {

        if (Trace.attachment) {
            Trace.log("Start processing attachment creation event. EventId: {}, Source: {}",
                    event.getEventId(), event.getSource());
        }

        try {
            this.attachmentService.updateAttachmentAfterUploadAsync(event.getAttachmentUploadRequestDto());

            if (Trace.attachment) {
                Trace.log("Attachment updated successfully for EventId: {}", event.getEventId());
            }
        } catch (Exception e) {
            if (Trace.attachment) {
                Trace.log("Error processing attachment creation event. EventId: {}", event.getEventId(), e);
            }
        }
    }

    @Override
    public String getType() {
        return EventMessageType.ATTACHMENT_CREATE.getEventType();
    }
}

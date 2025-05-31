package com.suchee.app.messaging.async;

import com.suchee.app.exception.AsyncHandlerNotFoundException;
import com.suchee.app.handler.AsyncHandler;
import com.suchee.app.handler.AsyncHandlerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AsyncEventConsumer {

    @Autowired
    private AsyncHandlerManager asyncHandlerManager;

    @KafkaListener(topics = {"notification.queue"}, groupId = "notification-consumer-group")
    public void handleEvent(AsyncMessage event) throws AsyncHandlerNotFoundException {

        AsyncHandler handler = this.asyncHandlerManager.getAsyncHandlerForEvent(event.getType());

        handler.doHandle(event);
    }

}

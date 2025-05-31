package com.suchee.app.messaging.async;

import com.suchee.app.enums.EventMessageType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Represents a generic asynchronous message payload.
 *
 * @param <T> the type of the message content
 */

@Data
public abstract class AsyncMessage<T> implements Serializable {

    // Async events can be published in two ways
    // SpringEvent or QueueEvent

    private AsyncEventPublishType asyncEventPublishType;
    private QueueType queueType;

    public AsyncMessage(){

    }

    public AsyncMessage(AsyncEventPublishType asyncEventPublishType,QueueType queueType){
        this.asyncEventPublishType=asyncEventPublishType;
        this.queueType=queueType;
    }

    /**
     * Returns the message content.
     *
     * @return the message payload of type T
     */
   public abstract T getMessage();

    /**
     * Returns the type of the event message.
     *
     * @return the {@link EventMessageType} indicating the message category
     */
   public abstract EventMessageType getType();




}

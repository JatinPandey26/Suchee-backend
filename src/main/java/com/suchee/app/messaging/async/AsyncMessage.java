package com.suchee.app.messaging.async;

import com.suchee.app.enums.EventMessageType;
import java.io.Serializable;

/**
 * Represents a generic asynchronous message payload.
 *
 * @param <T> the type of the message content
 */
public interface AsyncMessage<T> extends Serializable {

    /**
     * Returns the message content.
     *
     * @return the message payload of type T
     */
    T getMessage();

    /**
     * Returns the type of the event message.
     *
     * @return the {@link EventMessageType} indicating the message category
     */
    EventMessageType getType();
}

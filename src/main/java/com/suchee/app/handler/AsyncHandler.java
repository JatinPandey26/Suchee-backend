package com.suchee.app.handler;

import com.suchee.app.enums.EventMessageType;
import com.suchee.app.messaging.async.AsyncMessage;

public interface AsyncHandler<T extends AsyncMessage> {

    void doHandle(T message);

    EventMessageType getEventType();

}

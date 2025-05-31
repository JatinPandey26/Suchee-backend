package com.suchee.app.messaging.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public interface EventQueuePublisher {

    void publish(AsyncMessage payload);
}


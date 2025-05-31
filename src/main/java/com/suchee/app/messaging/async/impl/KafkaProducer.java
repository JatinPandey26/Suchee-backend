package com.suchee.app.messaging.async.impl;

import com.suchee.app.messaging.async.AsyncMessage;
import com.suchee.app.messaging.async.EventQueuePublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer implements EventQueuePublisher {

    private final KafkaTemplate<String,AsyncMessage> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, AsyncMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(AsyncMessage payload) {
        this.kafkaTemplate.send(payload.getQueueType().getQueueName() , payload);
    }
}

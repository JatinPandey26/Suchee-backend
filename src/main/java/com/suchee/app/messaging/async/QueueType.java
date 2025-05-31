package com.suchee.app.messaging.async;

public enum QueueType {
    NOTIFICATION("notification.queue");

    private final String queueName;

    QueueType(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }
}

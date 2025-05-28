package com.suchee.app.enums;

public enum EventMessageType {

    ATTACHMENT_CREATE("ATTACHMENT_CREATED");

    private final String eventType;

    EventMessageType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        return eventType;
    }
}

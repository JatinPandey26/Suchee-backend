package com.suchee.app.enums;

public enum EventMessageType {

    ATTACHMENT_CREATE("ATTACHMENT_CREATED"),
    MEMBER_INVITE_CREATED("MEMBER_INVITE_CREATED"),
    USER_CREATED("USER_CREATED");

    private final String eventType;

    EventMessageType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventMessageType() {
        return eventType;
    }

    @Override
    public String toString() {
        return eventType;
    }
}

package com.suchee.app.messaging.async;

/**
 * Enum representing the types of asynchronous event publishing mechanisms.
 */
public enum AsyncEventPublishType {

    /** Publish event via Spring application event system */
    SPRING_EVENT("springEvent"),

    /** Publish event via external message queue */
    QUEUE_EVENT("queueEvent");

    private final String eventType;

    /**
     * Constructs an AsyncEventPublishType with the given string representation.
     *
     * @param eventType the string identifier of the event publish type
     */
    AsyncEventPublishType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Returns the string representation of the event publish type.
     *
     * @return the event type string
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Parses the given string to return the matching AsyncEventPublishType enum.
     *
     * @param type the string representation of the event publish type
     * @return the corresponding AsyncEventPublishType
     * @throws IllegalArgumentException if the string does not match any enum value
     */
    public static AsyncEventPublishType fromString(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Event publish type cannot be null");
        }

        for (AsyncEventPublishType value : AsyncEventPublishType.values()) {
            if (value.eventType.equalsIgnoreCase(type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown event publish type: " + type);
    }
}

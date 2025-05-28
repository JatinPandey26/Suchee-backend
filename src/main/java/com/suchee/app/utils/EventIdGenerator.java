package com.suchee.app.utils;

import java.util.UUID;

/**
 * Utility class for generating unique event identifiers.
 *
 * <p>This class provides a method to generate random UUID strings
 * to be used as unique IDs for events or other purposes.</p>
 *
 * <p>UUIDs generated are of standard 36-character string format.</p>
 *
 * Usage example:
 * <pre>
 *     String eventId = EventIdGenerator.generateId();
 * </pre>
 *
 * No instance of this class is needed; all methods are static.
 */
public class EventIdGenerator {

    /**
     * Generates a new unique identifier as a string.
     *
     * @return a random UUID string in the standard 36-character format
     */
    public static String generateId(){
        return UUID.randomUUID().toString();
    }
}

package com.suchee.app.messaging.async;

/**
 * Defines a contract for processing asynchronous events of a specific type.
 *
 * @param <T> the type of event to be processed
 */
public interface AsyncProcessor<T> {

    /**
     * Processes the given event.
     *
     * @param event the event instance to be processed
     */
    void process(T event);

    /**
     * Returns the type identifier of the event this processor handles.
     *
     * @return the string representation of the event type
     */
    String getType();
}

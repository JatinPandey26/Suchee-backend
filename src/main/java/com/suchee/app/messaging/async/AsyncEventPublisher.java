package com.suchee.app.messaging.async;

import com.suchee.app.logging.Trace;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publishes asynchronous events to the appropriate channels:
 * either Spring application events or an external queue.
 */
@Component
public class AsyncEventPublisher {

    private final ApplicationEventPublisher springPublisher;
    private final EventQueuePublisher queuePublisher;

    /**
     * Constructs the AsyncEventPublisher with Spring and queue publishers.
     *
     * @param springPublisher Spring ApplicationEventPublisher instance
     * @param queuePublisher  EventQueuePublisher instance for queue events
     */
    public AsyncEventPublisher(ApplicationEventPublisher springPublisher,
                               EventQueuePublisher queuePublisher) {
        if (springPublisher == null) {
            throw new IllegalArgumentException("springPublisher cannot be null");
        }
        if (queuePublisher == null) {
            throw new IllegalArgumentException("queuePublisher cannot be null");
        }
        this.springPublisher = springPublisher;
        this.queuePublisher = queuePublisher;
    }

    /**
     * Publishes the given AsyncMessage event according to its configured publish type.
     *
     * @param event the AsyncMessage event to publish
     * @throws IllegalArgumentException if the event is null or has unsupported type
     */
    public void publish(AsyncMessage event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }

        switch (event.getAsyncEventPublishType()) {
            case SPRING_EVENT:
                springPublisher.publishEvent(event);
                if (Trace.asyncEvent) {
                    Trace.log("Published SPRING_EVENT: " + event);
                }
                break;

            case QUEUE_EVENT:
                queuePublisher.publish(event); // TODO: make queue dynamic in future
                if (Trace.asyncEvent) {
                    Trace.log("Published QUEUE_EVENT: " + event);
                }
                break;

            default:
                throw new IllegalArgumentException("Unsupported event type: " + event.getAsyncEventPublishType());
        }
    }
}

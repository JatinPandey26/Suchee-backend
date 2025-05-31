package com.suchee.app.handler;

import com.suchee.app.enums.EventMessageType;
import com.suchee.app.exception.AsyncHandlerNotFoundException;
import com.suchee.app.logging.Trace;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages registration and retrieval of AsyncHandler instances
 * based on the event message type they handle.
 */
@Component
public class AsyncHandlerManager {

    @Autowired
    private List<AsyncHandler> asyncHandlers;

    private final Map<String, AsyncHandler> asyncHandlerMap = new HashMap<>();

    /**
     * Registers all AsyncHandlers available in the Spring context,
     * mapping them by their event message type.
     */
    @PostConstruct
    private void registerHandlers() {
        if (asyncHandlers == null || asyncHandlers.isEmpty()) {
            if (Trace.asyncEvent) {
                Trace.log("No AsyncHandlers found to register.");
            }
            return;
        }

        for (AsyncHandler handler : asyncHandlers) {
            String eventType = handler.getEventType().getEventMessageType();
            asyncHandlerMap.put(eventType, handler);
            if (Trace.asyncEvent) {
                Trace.log("Registered AsyncHandler for event type: " + eventType);
            }
        }
    }

    /**
     * Retrieves the AsyncHandler responsible for the given event message type.
     *
     * @param eventMessageType the type of the event message
     * @return AsyncHandler registered for this event type
     * @throws AsyncHandlerNotFoundException if no handler is found for the event type
     */
    public AsyncHandler getAsyncHandlerForEvent(EventMessageType eventMessageType) throws AsyncHandlerNotFoundException {
        if (eventMessageType == null) {
            throw new IllegalArgumentException("EventMessageType cannot be null");
        }

        String messageType = eventMessageType.getEventMessageType();
        AsyncHandler handler = this.asyncHandlerMap.get(messageType);

        if (handler == null) {
            throw new AsyncHandlerNotFoundException("No AsyncHandler found for event type: " + messageType);
        }

        if (Trace.asyncEvent) {
            Trace.log("Found AsyncHandler for event type: " + messageType);
        }

        return handler;
    }
}

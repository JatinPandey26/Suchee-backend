package com.suchee.app.core.entities;

import com.suchee.app.messaging.listeners.AuditEntityListener;
import jakarta.persistence.EntityListeners;

/**
 * Interface to be implemented by entities that support auditing.
 *
 * Entities implementing this interface can be associated with an audit listener
 * that can track changes or perform audit-related logic during entity lifecycle events.
 *
 * The {@link AuditEntityListener} class (specified in {@link EntityListeners})
 * will listen to lifecycle events (e.g., persist, update) for entities implementing this interface.
 */
@EntityListeners(AuditEntityListener.class)
public interface Auditable {

    /**
     * Returns the unique identifier of the entity.
     *
     * @return the entity ID
     */
    Long getId();

    /**
     * Returns a name identifying the entity type for audit purposes.
     *
     * @return audit entity name
     */
    String getAuditEntityName();
}


package com.suchee.app.core.entities;


import java.time.LocalDateTime;

/**
 * Interface defining auditing timestamps for entities.
 *
 * Provides methods to get and set creation and last update timestamps.
 * Implementing classes can track when they were created and last modified.
 */
public interface TimeStamped {

    /**
     * Gets the timestamp when the entity was created.
     *
     * @return the creation timestamp
     */
    LocalDateTime getCreatedAt();

    /**
     * Sets the timestamp when the entity was created.
     *
     * @param createdAt the creation timestamp to set
     */
    void setCreatedAt(LocalDateTime createdAt);

    /**
     * Gets the timestamp when the entity was last updated.
     *
     * @return the last updated timestamp
     */
    LocalDateTime getUpdatedAt();

    /**
     * Sets the timestamp when the entity was last updated.
     *
     * @param updatedAt the last updated timestamp to set
     */
    void setUpdatedAt(LocalDateTime updatedAt);
}


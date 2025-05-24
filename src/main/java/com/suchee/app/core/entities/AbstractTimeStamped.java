package com.suchee.app.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * Abstract base class that provides timestamp auditing fields to entities.
 * Implements the {@link TimeStamped} interface to provide
 * standard getter and setter methods for creation and update timestamps.
 *
 * This class can be extended by entities that require tracking
 * of when they were created and last modified.
 */
public abstract class AbstractTimeStamped implements TimeStamped {

    /**
     * Timestamp indicating when the entity was created.
     * Typically set once when the entity is first persisted.
     */
    @Column
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the entity was last updated.
     * Updated each time the entity is modified and persisted.
     */
    @Column
    private LocalDateTime updatedAt;

    /**
     * Gets the creation timestamp of the entity.
     *
     * @return the creation timestamp
     */
    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the entity.
     * Usually set once when the entity is created.
     *
     * @param createdAt the creation timestamp to set
     */
    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the last updated timestamp of the entity.
     *
     * @return the last updated timestamp
     */
    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the last updated timestamp of the entity.
     * Should be updated whenever the entity is modified.
     *
     * @param updatedAt the last updated timestamp to set
     */
    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}

package com.suchee.app.core.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Abstract base class that provides automatic auditing of creation and update timestamps
 * for entities. It uses JPA auditing features to automatically populate the timestamp
 * fields when an entity is persisted or updated.
 *
 * <p>This class should be extended by entity classes that need to track when
 * they were created and last modified.</p>
 *
 * <p>It implements the {@link TimeStamped} interface, which defines standard
 * getter and setter methods for timestamp fields such as {@code createdAt} and {@code updatedAt}.</p>
 *
 * <p>Annotated with {@link MappedSuperclass} to allow its fields to be inherited by subclasses,
 * and with {@link EntityListeners} to enable auditing via {@link AuditingEntityListener}.</p>
 *
 * @see TimeStamped
 * @see AuditingEntityListener
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractTimeStamped implements TimeStamped {

    /**
     * Timestamp indicating when the entity was created.
     * Typically set once when the entity is first persisted.
     */
    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the entity was last updated.
     * Updated each time the entity is modified and persisted.
     */
    @Column
    @LastModifiedDate
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

}

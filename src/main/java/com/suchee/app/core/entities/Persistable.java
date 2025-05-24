package com.suchee.app.core.entities;

/**
 * Interface representing an entity that can be persisted.
 *
 * Extends {@link BaseEntity} to inherit serialization and cloning capabilities,
 * and {@link TimeStamped} to include creation and update timestamps.
 *
 * Defines methods that are essential for persistence lifecycle and auditing:
 * - {@code getId()} to get the unique identifier of the entity.
 * - {@code isNew()} to check if the entity is new (not persisted yet).
 * - {@code getLastUser()} to get the ID of the user who last modified the entity.
 */
public interface Persistable extends BaseEntity, TimeStamped {

    /**
     * Returns the unique identifier of this entity.
     *
     * @return the entity's ID
     */
    Long getId();

    /**
     * Indicates whether the entity has not yet been persisted.
     * This can be used by persistence frameworks to decide
     * between insert and update operations.
     *
     * @return {@code true} if the entity is new, {@code false} otherwise
     */
    boolean isNew();

    /**
     * Returns the identifier of the user who last modified this entity.
     * Useful for audit tracking.
     *
     * @return the ID of the last modifying user
     */
    Long getLastUser();
}

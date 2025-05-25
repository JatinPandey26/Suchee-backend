package com.suchee.app.core.entities;

import com.suchee.app.entity.UserAccount;

/**
 * Represents an entity that has an associated owner.
 * Typically used for access control, auditing, or ownership tracking.
 */
public interface Ownable {

    /**
     * Returns the owner of the entity.
     *
     * @return the {@link UserAccount} that owns this entity
     */
    UserAccount getOwner();

    /**
     * Sets the owner of the entity.
     *
     * @param owner the {@link UserAccount} to be set as the owner
     */
    void setOwner(UserAccount owner);
}
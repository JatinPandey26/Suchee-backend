package com.suchee.app.core.entities;

import com.suchee.app.logging.Trace;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;

/**
 * Abstract base class that adds persistence-related properties and behavior
 * to entities. It extends {@link AbstractTimeStamped} to include timestamp fields,
 * and implements {@link Persistable} interface for persistence lifecycle.
 */
@MappedSuperclass
public abstract class AbstractPersistable extends AbstractTimeStamped implements Persistable {

    /**
     * Flag indicating whether the entity is new (not yet persisted).
     * This is used by persistence frameworks to determine if an insert or update is needed.
     */
    @Transient
    private boolean isNew = true;

    /**
     * Stores the identifier of the user who last modified this entity.
     * Can be used for audit or tracking purposes.
     */
    @Column
    private Long lastUser;

    /**
     * Checks if this entity is new (not persisted yet).
     * Used by the persistence mechanism to decide insert vs update.
     *
     * @return true if entity is new, false otherwise
     */
    @Override
    public boolean isNew() {
        return isNew;
    }

    /**
     * Sets the newness state of this entity.
     *
     * @param aNew true if entity is new, false otherwise
     */
    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    /**
     * Gets the ID of the user who last updated this entity.
     *
     * @return ID of last user who modified the entity
     */
    @Override
    public Long getLastUser() {
        return lastUser;
    }

    /**
     * Sets the ID of the user who last modified this entity.
     *
     * @param lastUser ID of the last modifying user
     */
    public void setLastUser(Long lastUser) {
        this.lastUser = lastUser;
    }

    @PrePersist
    public void setLastUserAsCurrentUser(){
        // TODO: fetch currentUser from security context and set
        this.lastUser=null;
    }

     public static String getEntityName(){
         Trace.log("Entity Name not configured , Shadow this method in entity to configure entity name");
        throw new RuntimeException("Entity Name not configured in respective entity");
     }

}


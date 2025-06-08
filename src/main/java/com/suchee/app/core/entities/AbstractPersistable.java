package com.suchee.app.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suchee.app.entity.UserAccount;
import com.suchee.app.logging.Trace;
import com.suchee.app.security.SecurityContext;
import jakarta.persistence.*;


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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_user_id")
    @JsonIgnore
    private UserAccount lastUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private UserAccount createdBy;

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
    public UserAccount getLastUser() {
        return lastUser;
    }

    /**
     * Sets the ID of the user who last modified this entity.
     *
     * @param lastUser ID of the last modifying user
     */
    public void setLastUser(UserAccount lastUser) {
        this.lastUser = lastUser;
    }

    @PreUpdate
    public void setLastUserAsCurrentUser(){
        UserAccount userAccount = SecurityContext.getCurrentUserAccount();
        this.lastUser = userAccount;
    }

    @PrePersist
    public  void setCreatedBy(){
        UserAccount userAccount = SecurityContext.getCurrentUserAccount();
        this.createdBy = userAccount;
        this.lastUser=userAccount;
    }



     public static String getEntityName(){
         Trace.log("Entity Name not configured , Shadow this method in entity to configure entity name");
        throw new RuntimeException("Entity Name not configured in respective entity");
     }

}


package com.suchee.app.core.entities;

import com.suchee.app.entity.UserAccount;
import jakarta.persistence.*;

/**
 * A base class for entities that are owned by a {@link UserAccount}.
 * <p>
 * This abstract class provides a standard mapping for ownership.
 * Subclasses inherit the {@code owner} field and its associated getter and setter.
 * It is useful for tracking which user created or is responsible for the entity.
 * </p>
 *
 * <p>
 * Note: This is a unidirectional mapping from the owned entity to the {@link UserAccount}.
 * The {@code UserAccount} does not maintain a collection of owned entities.
 * </p>
 */

@MappedSuperclass
public abstract class AbstractOwnable extends AbstractPersistable implements Ownable{

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserAccount owner;
    // in userAccount we will not track this
}

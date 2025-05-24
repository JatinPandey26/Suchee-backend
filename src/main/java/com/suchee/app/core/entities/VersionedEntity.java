package com.suchee.app.core.entities;

import org.springframework.util.SerializationUtils;

/**
 * Abstract base class for versioned entities.
 *
 * Entities extending this class will maintain a history of their changes.
 * Before persisting any updates, the current state will be saved to a history table
 * with a version identifier.
 */
public abstract class VersionedEntity extends AbstractPersistable {

    /**
     * Snapshot of the entity representing the previous version.
     * This snapshot is typically created when the entity is loaded and used
     * to compare or persist historical state before updates.
     */
    protected VersionedEntity snapshot;

    /**
     * Gets the snapshot (previous version) of this entity.
     *
     * @return the snapshot
     */
    public VersionedEntity getSnapshot() {
        return snapshot;
    }

    /**
     * Sets the snapshot (previous version) of this entity.
     *
     * @param snapshot the previous version to be set
     */
    public void setSnapshot(VersionedEntity snapshot) {
        this.snapshot = snapshot;
    }

    /**
     * Create a copy of the current entity to be used as a snapshot.
     * This should be implemented by concrete subclasses to return a deep copy
     * of the current state for history tracking.
     *
     * @return a new VersionedEntity representing the current state
     */
    public VersionedEntity createSnapshot(VersionedEntity me){
        return SerializationUtils.clone(me);
    }
}

package com.suchee.app.core.entities;

/**
 * Abstract base class for entities that do not require versioning or history tracking.
 *
 * Entities extending this class will be persisted without maintaining
 * any previous versions or snapshots.
 *
 * This class inherits from AbstractPersistable to provide common persistence behavior.
 */
public abstract class NonVersioned extends AbstractPersistable {

}


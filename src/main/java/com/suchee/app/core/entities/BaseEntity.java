package com.suchee.app.core.entities;

import java.io.Serializable;

/**
 * Base interface for all entity classes in the application.
 *
 * Extends {@link Serializable} to allow entities to be serialized,
 * and {@link Cloneable} to allow creating copies of entities.
 *
 * This interface can be used as a common supertype for all entities,
 * enabling generic handling or type safety.
 */
public interface BaseEntity extends Serializable, Cloneable {

    static String getEntityName(){
        throw new RuntimeException("Entity name not specified in respective entity.");
    }

}


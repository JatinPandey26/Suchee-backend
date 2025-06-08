package com.suchee.app.entity;

import com.suchee.app.core.entities.VersionedEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Team extends VersionedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true,nullable = false,length = 60)
    private String name;

    @Column
    private String description;

    // Invitations List TODO
    @Column(length = 600)
    private String avatar;

    @Override
    public Long getId() {
        return id;
    }

    public static String getEntityName() {
        return "Team";
    }

}

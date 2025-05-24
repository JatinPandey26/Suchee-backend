package com.suchee.app.entity;

import com.suchee.app.core.entities.NonVersioned;
import com.suchee.app.enums.RoleType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role extends NonVersioned {

    @Id
    private Long id;

    @Column

    @OneToMany(mappedBy = "role")
    private List<UserAccount> users;

    @Column
    @Embedded
    private RoleType Role;

    /**
     * @return
     */
    @Override
    public Long getId() {
        return this.id;
    }
}

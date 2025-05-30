package com.suchee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suchee.app.core.entities.NonVersioned;
import com.suchee.app.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Role extends NonVersioned {

    @Id
    private Long id;


    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<UserAccount> users;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column
    private String description;

    /**
     * @return
     */
    @Override
    public Long getId() {
        return this.id;
    }

    public static String getEntityName() {
        return "Role";
    }


}

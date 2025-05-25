package com.suchee.app.entity;

import com.suchee.app.core.entities.NonVersioned;
import com.suchee.app.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Entity
@Data
public class Role extends NonVersioned {

    @Id
    private Long id;


    @ManyToMany(mappedBy = "roles")
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

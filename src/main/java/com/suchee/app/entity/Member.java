package com.suchee.app.entity;

import com.suchee.app.core.entities.VersionedEntity;
import com.suchee.app.enums.MemberStatus;
import com.suchee.app.enums.NotificationPreferenceOption;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Member extends VersionedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    // a user can be part of many teams
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;

    @ManyToMany
    @JoinTable(name = "member_x_role",
                joinColumns = {@JoinColumn(name = "member_id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id")} )
    private Set<Role> roles;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private NotificationPreferenceOption notificationPreference = NotificationPreferenceOption.ALL;

    @Override
    public Long getId() {
        return id;
    }

    public static String getEntityName() {
        return "Member";
    }

}


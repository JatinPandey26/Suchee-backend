package com.suchee.app.entity;

import com.suchee.app.core.entities.NonVersioned;
import com.suchee.app.enums.MemberInvitationStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MemberInvitation extends NonVersioned {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    private String email;

    @Column
    private Team team;

    @Enumerated(EnumType.STRING)
    private MemberInvitationStatus status = MemberInvitationStatus.PENDING;

    @OneToOne
    @JoinColumn(name = "role")
    private Role role; // only one role - TEAM_ADMIN or TEAM_MEMBER

    // all users invited will be onboarded as TEAM_MEMBER

    @Override
    public Long getId() {
        return id;
    }

    public static String getEntityName() {
        return "MemberInvitation";
    }

}

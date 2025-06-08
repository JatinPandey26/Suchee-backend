package com.suchee.app.dto;

import com.suchee.app.enums.MemberInvitationStatus;
import com.suchee.app.enums.RoleType;
import lombok.Data;

@Data
public class MemberInvitationCreateDto {
    private String email;
    private RoleType role;
}

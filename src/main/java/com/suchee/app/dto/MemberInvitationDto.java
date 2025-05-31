package com.suchee.app.dto;

import com.suchee.app.enums.MemberInvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInvitationDto {
    private long id;
    private String email;
    private TeamDTO team;
    private MemberInvitationStatus status;
}

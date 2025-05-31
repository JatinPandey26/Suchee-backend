package com.suchee.app.service;

import com.suchee.app.dto.TeamDTO;
import com.suchee.app.enums.MemberInvitationStatus;

public interface MemberInvitationService {

    boolean createMemberInvitation(TeamDTO teamId , String email);

    void changeStatusOfInvitation(long invitationId , MemberInvitationStatus status);
}

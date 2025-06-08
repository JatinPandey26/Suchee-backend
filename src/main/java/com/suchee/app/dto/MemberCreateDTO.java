package com.suchee.app.dto;

import com.suchee.app.enums.MemberStatus;
import com.suchee.app.enums.NotificationPreferenceOption;
import lombok.Data;

import java.util.Set;

@Data
public class MemberCreateDTO {

    private TeamDTO team;
    private UserDTO user;
    private Set<RoleDTO> roles;
    private MemberStatus status;
    private NotificationPreferenceOption notificationPreference;

}

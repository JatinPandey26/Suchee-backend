package com.suchee.app.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class TeamCreationDTO {
    private String name;
    private String description;
    private String avatar;
    private List<MemberInvitationCreateDto> invitations;
}

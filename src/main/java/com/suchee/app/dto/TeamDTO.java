package com.suchee.app.dto;

import com.suchee.app.entity.Attachment;
import lombok.Data;

@Data
public class TeamDTO {

    private Long id;
    private String teamName;
    private String description;
    private UserDTO createdBy;
    private AttachmentDTO avatar;

}

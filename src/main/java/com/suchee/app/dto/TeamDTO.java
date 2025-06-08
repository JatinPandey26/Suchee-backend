package com.suchee.app.dto;

import com.suchee.app.entity.Attachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO extends AbstractPersistableDTO {

    private Long id;
    private String name;
    private String description;
    private UserDTO createdBy;
    private String avatar;

}

package com.suchee.app.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamCreationDTO {
    private String teamName;
    private String description;
    private MultipartFile avatar;
}

package com.suchee.app.dto;

import com.suchee.app.enums.FileType;
import com.suchee.app.enums.StorageProvider;
import lombok.Data;

@Data
public class AttachmentDTO {
    private Long id;
    private String name;
    private String url;
    private String previewUrl;
    private UserDTO uploadedBy;
    private Long fileSize;
    private StorageProvider storageProvider;
    private FileType fileType;
}

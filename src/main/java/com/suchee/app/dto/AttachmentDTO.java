package com.suchee.app.dto;

import com.suchee.app.enums.FileType;
import com.suchee.app.enums.StorageProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

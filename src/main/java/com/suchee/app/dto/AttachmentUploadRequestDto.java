package com.suchee.app.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AttachmentUploadRequestDto {
    private long attachmentId;
    private String filePath;
    private String publicId;
    private String preUploadUrl;
    private String folderPath;
    private MultipartFile file;
}

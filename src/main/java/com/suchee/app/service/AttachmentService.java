package com.suchee.app.service;

import com.suchee.app.dto.AttachmentDTO;
import com.suchee.app.dto.AttachmentStorageInfoDto;
import com.suchee.app.dto.AttachmentUploadRequestDto;
import com.suchee.app.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {

    public Attachment process(MultipartFile file, AttachmentStorageInfoDto attachmentStorageInfo);
    public List<Attachment> process(List<MultipartFile> files, AttachmentStorageInfoDto attachmentStorageInfo);
    AttachmentDTO updateAttachmentAfterUploadAsync(AttachmentUploadRequestDto attachmentUploadRequest);
}

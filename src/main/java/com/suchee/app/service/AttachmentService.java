package com.suchee.app.service;

import com.suchee.app.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {

    public Attachment process(MultipartFile file, String folderName);
    public Attachment process(MultipartFile file);
    public List<Attachment> process(List<MultipartFile> files, String folderName);
    public List<Attachment> process(List<MultipartFile> files);




}

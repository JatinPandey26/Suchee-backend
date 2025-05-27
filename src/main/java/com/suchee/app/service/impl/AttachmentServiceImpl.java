package com.suchee.app.service.impl;

import com.suchee.app.entity.Attachment;
import com.suchee.app.enums.FileType;
import com.suchee.app.enums.StorageProvider;
import com.suchee.app.logging.Trace;
import com.suchee.app.repository.AttachmentRepository;
import com.suchee.app.service.AttachmentService;
import com.suchee.app.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * Service implementation for processing and storing attachments.
 * Handles file uploads using the configured FileStorageService and persists metadata.
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final FileStorageService fileStorageService;
    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(FileStorageService fileStorageService, AttachmentRepository attachmentRepository) {
        this.fileStorageService = fileStorageService;
        this.attachmentRepository = attachmentRepository;
    }

    /**
     * Processes and stores a single file in the specified folder.
     h
     * @param file       the file to process
     * @param folderPath the folder where the file should be uploaded
     * @return the saved Attachment entity
     */
    public Attachment process(MultipartFile file, String folderPath) {

        if (Trace.attachment) {
            Trace.log("Starting to process attachment in folder: " + folderPath);
            Trace.debug("File name: ", file.getOriginalFilename(), "Size: ", file.getSize());
        }

        Attachment attachment = new Attachment();

        String url = this.fileStorageService.uploadFile(file, folderPath);
        attachment.setUrl(url);

        // TODO: if optimization possible utilize preview URL generation
        attachment.setName(file.getOriginalFilename());
        attachment.setPreviewUrl(url);
        attachment.setFileSize(file.getSize());
        attachment.setFileType(FileType.fromExtension(file.getOriginalFilename()));
        attachment.setStorageProvider(StorageProvider.CLOUDINARY);
        attachment.setPath(folderPath);

        Attachment savedAttachment = this.attachmentRepository.save(attachment);

        if (Trace.attachment) {
            Trace.log("Attachment saved with URL: " + savedAttachment.getUrl());
        }

        return savedAttachment;
    }

    /**
     * Processes and stores a single file in the default folder.
     *
     * @param file the file to process
     * @return the saved Attachment entity
     */
    @Override
    public Attachment process(MultipartFile file) {
        return process(file, this.fileStorageService.getDefaultFolderPath());
    }

    /**
     * Processes and stores multiple files in the specified folder.
     *
     * @param files      the list of files to process
     * @param folderPath the folder where the files should be uploaded
     * @return the list of saved Attachment entities
     */
    @Override
    public List<Attachment> process(List<MultipartFile> files, String folderPath) {
        if (Trace.attachment) {
            Trace.log("Processing " + files.size() + " attachments in folder: " + folderPath);
        }

        return files.stream()
                .map(file -> process(file, folderPath))
                .toList();
    }

    /**
     * Processes and stores multiple files in the default folder.
     *
     * @param files the list of files to process
     * @return the list of saved Attachment entities
     */
    @Override
    public List<Attachment> process(List<MultipartFile> files) {
        return process(files, this.fileStorageService.getDefaultFolderPath());
    }
}
